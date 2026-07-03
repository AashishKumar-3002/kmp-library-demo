import Foundation
import UnloqOffersCore

@objc(UNQOffersBridge)
public final class UNQOffers: NSObject {
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
}
