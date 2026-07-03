import Foundation
import UnloqOffersCore

public final class UNQOffers {
    private let client = UnloqOffersCoreClient()

    public init() {}

    public func initialize(merchantId: String, widgetBaseUrl: String) {
        client.initialize(
            config: OfferSdkConfig(
                merchantId: merchantId,
                environment: .qa,
                widgetBaseUrl: widgetBaseUrl
            )
        )
    }

    public func setUser(id: String, loyaltyTier: String) {
        client.setUser(user: OfferUser(id: id, loyaltyTier: loyaltyTier))
    }

    public func setAttribution(source: String, campaign: String) {
        client.setAttribution(attribution: OfferAttribution(source: source, campaign: campaign))
    }

    public func emitEvent(name: String, value: String) {
        client.emitEvent(event: OfferEvent(name: name, value: value))
    }

    public func bannerText(cartValue: Int64) -> String {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        return "\(result.title): \(result.rewardText)"
    }

    public func widgetUrl(cartValue: Int64) -> String {
        let result = client.evaluate(cartValue: cartValue, currency: "INR")
        return result.widgetUrl
    }
}
