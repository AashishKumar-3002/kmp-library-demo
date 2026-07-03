package poc.demo

import com.useunloq.offers.android.UnloqOffersAndroidSdk
import com.useunloq.offers.core.OfferEnvironment
import merchant.demo.OfferUsage

fun main() {
    val merchantUsage = OfferUsage().apply { setup() }
    val androidSdk = UnloqOffersAndroidSdk().apply {
        initialize(
            merchantId = "merchant_123",
            environment = OfferEnvironment.QA,
            widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
        )
        setUser(id = "android_user", loyaltyTier = "gold")
        setAttribution(source = "appsflyer", campaign = "poc")
        emitEvent(name = "checkout_started", value = "1")
    }

    val androidDecision = androidSdk.evaluate(cartValue = 7500, currency = "INR")

    println("KMP merchant direct usage")
    println(merchantUsage.renderOffer())
    println(merchantUsage.showWidget())
    println()
    println("Android facade usage")
    println(androidSdk.getBannerText("merchant_123", 7500))
    println(androidDecision.widgetUrl)
    println(androidDecision.debugSummary)
}
