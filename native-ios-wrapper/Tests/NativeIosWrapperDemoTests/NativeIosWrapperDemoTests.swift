import XCTest
@testable import NativeIosWrapperDemo

final class NativeIosWrapperDemoTests: XCTestCase {
    func testSwiftWrapperDelegatesToKmpCore() {
        let sdk = UNQOffers()
        sdk.initialize(
            merchantId: "merchant_123",
            widgetBaseUrl: "https://qa-sdk.useunloq.com/widget"
        )
        sdk.setUser(id: "ios_user", loyaltyTier: "gold")
        sdk.setAttribution(source: "branch", campaign: "poc")
        sdk.emitEvent(name: "checkout_started", value: "1")

        XCTAssertTrue(sdk.bannerText(cartValue: 7500).contains("Unlock 10% cashback"))
        XCTAssertTrue(sdk.widgetUrl(cartValue: 7500).contains("userId=ios_user"))
    }
}
