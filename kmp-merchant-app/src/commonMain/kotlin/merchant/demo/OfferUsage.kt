package merchant.demo

import com.useunloq.offers.kmp.OfferEnvironment
import com.useunloq.offers.kmp.OfferWidgetHostContext
import com.useunloq.offers.kmp.UnloqOffers

class OfferUsage {
    private val offers = UnloqOffers()

    fun setup() {
        offers.initialize(
            merchantId = "merchant_123",
            environment = OfferEnvironment.QA,
            widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
        )
        offers.setUser(id = "kmp_user", loyaltyTier = "gold")
        offers.setAttribution(source = "branch", campaign = "poc")
        offers.emitEvent(name = "checkout_started", value = "1")
    }

    fun renderOffer(): String {
        val presentation = offers.showWidget(
            hostContext = OfferWidgetHostContext(
                screenName = "Checkout",
                hostId = "merchant_checkout"
            ),
            cartValue = 7500,
            currency = "INR"
        )

        return "${presentation.platformShell} -> ${presentation.summary} -> ${presentation.widgetUrl}"
    }

    fun showWidget(): String {
        val presentation = offers.showWidget(
            hostContext = OfferWidgetHostContext(
                screenName = "Checkout",
                hostId = "merchant_checkout"
            ),
            cartValue = 7500,
            currency = "INR"
        )

        return "${presentation.platformShell} -> ${presentation.summary} -> ${presentation.widgetUrl}"
    }
}
