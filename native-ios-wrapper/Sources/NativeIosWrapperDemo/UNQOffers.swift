import Foundation
import UIKit
import WebKit
import UnloqOffersCore

@objc(UNQOffersBridge)
public final class UNQOffers: NSObject, @unchecked Sendable {
    private let client = UnloqOffersCoreClient()

    @objc public override init() {}

    @objc public func initialize(merchantId: String, widgetBaseUrl: String) {
        client.initialize(
            config: OfferSdkConfig(
                merchantId: merchantId,
                environment: .qa,
                widgetBaseUrl: widgetBaseUrl
            )
        )
    }

    @objc public func setUser(id: String, loyaltyTier: String) {
        client.setUser(user: OfferUser(id: id, loyaltyTier: loyaltyTier))
    }

    @objc public func setAttribution(source: String, campaign: String) {
        client.setAttribution(attribution: OfferAttribution(source: source, campaign: campaign))
    }

    @objc public func emitEvent(name: String, value: String) {
        client.emitEvent(event: OfferEvent(name: name, value: value))
    }

    @objc public func bannerText(cartValue: Int64) -> String {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        return "\(result.title): \(result.rewardText)"
    }

    @objc public func widgetUrl(cartValue: Int64) -> String {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        return result.widgetUrl
    }

    @objc public func widgetPresentationSummary(screenName: String, hostId: String, cartValue: Int64) -> String {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        return "\(screenName) on \(hostId) -> \(result.rewardText)"
    }

    @objc public func showWidget(cartValue: Int64) {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        
        DispatchQueue.main.async {
            guard let windowScene = UIApplication.shared.connectedScenes.first(where: { $0.activationState == .foregroundActive }) as? UIWindowScene,
                  let window = windowScene.windows.first(where: { $0.isKeyWindow }),
                  let rootVC = window.rootViewController else {
                return
            }
            
            let config = WKWebViewConfiguration()
            let handler = UnloqScriptMessageHandler(delegate: self)
            config.userContentController.add(handler, name: "UnloqNative")
            
            let webVC = UIViewController()
            let webView = WKWebView(frame: webVC.view.bounds, configuration: config)
            webView.isOpaque = false
            webView.backgroundColor = .clear
            webView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
            webVC.view.addSubview(webView)
            
            let summary = "Checkout on kmp_merchant_ios -> \(result.rewardText)"
            let html = self.buildDemoWidgetHtml(summary: summary, widgetUrl: result.widgetUrl)
            webView.loadHTMLString(html, baseURL: URL(string: result.widgetUrl))
            
            webVC.modalPresentationStyle = .pageSheet
            if let sheet = webVC.sheetPresentationController {
                sheet.detents = [.medium(), .large()]
                sheet.prefersGrabberVisible = true
            }
            
            var topVC = rootVC
            while let presented = topVC.presentedViewController {
                topVC = presented
            }
            
            topVC.present(webVC, animated: true)
        }
    }
    
    @objc fileprivate func dismissWidget() {
        DispatchQueue.main.async {
            guard let windowScene = UIApplication.shared.connectedScenes.first(where: { $0.activationState == .foregroundActive }) as? UIWindowScene,
                  let window = windowScene.windows.first(where: { $0.isKeyWindow }),
                  let rootVC = window.rootViewController else {
                return
            }
            var topVC = rootVC
            while let presented = topVC.presentedViewController {
                topVC = presented
            }
            topVC.dismiss(animated: true)
        }
    }
    
    private func buildDemoWidgetHtml(summary: String, widgetUrl: String) -> String {
        return """
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
            </head>
            <body style="margin:0;font-family:sans-serif;background:transparent;color:#172033;">
                <div style="background:#fff8df; border-top-left-radius:24px; border-top-right-radius:24px; padding:24px; min-height: 100vh;">
                    <div style="width:36px;height:4px;background:#cccccc;border-radius:2px;margin:0 auto 20px;"></div>
                    <div style="font-size:12px;letter-spacing:0.12em;text-transform:uppercase;color:#7b5d00;">
                        UNLOQ Native iOS SDK
                    </div>
                    <h1 style="font-size:28px;line-height:1.1;margin:12px 0 8px;">
                        Offer widget shell
                    </h1>
                    <p style="font-size:16px;line-height:1.45;margin:0 0 18px;">
                        \(summary)
                    </p>
                    <div style="border-radius:18px;background:#ffffff;padding:16px;border:1px solid #ecd98a;">
                        <strong>Generated widget URL</strong>
                        <div style="font-size:12px;line-height:1.4;word-break:break-word;margin-top:8px;">
                            \(widgetUrl)
                        </div>
                    </div>
                    <button onclick="window.webkit.messageHandlers.UnloqNative.postMessage('continueWithOffer')" style="margin-top:20px;width:100%;border:0;border-radius:999px;padding:14px;background:#4f3b7f;color:white;font-size:16px;font-weight:700;">
                        Continue with offer
                    </button>
                </div>
            </body>
            </html>
        """
    }
}

class UnloqScriptMessageHandler: NSObject, WKScriptMessageHandler {
    weak var delegate: UNQOffers?
    
    init(delegate: UNQOffers) {
        self.delegate = delegate
    }
    
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if message.name == "UnloqNative", let body = message.body as? String, body == "continueWithOffer" {
            delegate?.dismissWidget()
        }
    }
}
