import SwiftUI
import NativeIosWrapperDemo

struct ContentView: View {
    @State private var resultText = "Tap the button to exercise the installed iOS SDK."
    private let offers = UNQOffers()

    var body: some View {
        NavigationStack {
            VStack(alignment: .leading, spacing: 16) {
                Text("UNLOQ KMP Merchant App")
                    .font(.title.bold())

                Text("This iOS app installs the native Swift SDK package. The Swift SDK depends on the KMP core XCFramework.")
                    .font(.body)

                Button("Show Offer Widget") {
                    offers.initialize(
                        merchantId: "merchant_123",
                        widgetBaseUrl: "https://qa-sdk.useunloq.com/widget"
                    )
                    offers.setUser(id: "kmp_ios_user", loyaltyTier: "gold")
                    offers.setAttribution(source: "kmp-merchant-app", campaign: "ios")
                    offers.emitEvent(name: "checkout_started", value: "1")

                    let summary = offers.widgetPresentationSummary(
                        screenName: "Checkout",
                        hostId: "kmp_merchant_ios",
                        cartValue: 7500
                    )
                    let widgetUrl = offers.widgetUrl(cartValue: 7500)

                    resultText = """
                    Platform shell: iOS Swift SDK
                    Summary: \(summary)
                    Widget URL: \(widgetUrl)
                    """
                }
                .buttonStyle(.borderedProminent)

                Text(resultText)
                    .font(.footnote.monospaced())
                    .textSelection(.enabled)
                    .frame(maxWidth: .infinity, alignment: .leading)

                Spacer()
            }
            .padding(24)
            .navigationTitle("Merchant iOS")
        }
    }
}
