import SwiftUI
import KmpMerchantShared

struct ContentView: View {
    @State private var resultText = "Tap the button to show the widget drawer."
    private let bridge = KmpMerchantBridge()

    var body: some View {
        NavigationStack {
            VStack(alignment: .leading, spacing: 16) {
                Text(bridge.title())
                    .font(.title.bold())

                Text(bridge.subtitle())
                    .font(.body)

                Button("Show Offer Widget") {
                    resultText = bridge.showOfferWidget()
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
