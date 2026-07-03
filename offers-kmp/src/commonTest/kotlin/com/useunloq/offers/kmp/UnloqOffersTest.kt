package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferEnvironment
import kotlin.test.Test
import kotlin.test.assertContains

class UnloqOffersTest {
    @Test
    fun `merchant-facing kmp sdk can evaluate and present widget`() {
        val offers = UnloqOffers()
        offers.initialize(
            merchantId = "merchant_123",
            environment = OfferEnvironment.QA,
            widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
        )
        offers.setUser(id = "merchant_user", loyaltyTier = "gold")
        offers.setAttribution(source = "branch", campaign = "poc")
        offers.emitEvent(name = "checkout_started", value = "1")

        val presentation = offers.showWidget(
            hostContext = OfferWidgetHostContext(
                screenName = "Checkout",
                hostId = "cart_screen"
            ),
            cartValue = 7500,
            currency = "INR"
        )

        assertContains(presentation.widgetUrl, "merchantId=merchant_123")
        assertContains(presentation.widgetUrl, "userId=merchant_user")
        assertContains(presentation.summary, "Unlock 10% cashback")
    }
}
