package com.useunloq.offers.android

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue

class UnloqOffersAndroidSdkTest {
    @Test
    fun `android wrapper delegates to shared core`() {
        val sdk = UnloqOffersAndroidSdk()
        sdk.initialize(
            merchantId = "merchant_123",
            environment = "QA",
            widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
        )
        sdk.setUser(id = "android_user", loyaltyTier = "gold")
        sdk.setAttribution(source = "appsflyer", campaign = "poc")
        sdk.emitEvent(name = "checkout_started", value = "1")

        val decision = sdk.evaluate(cartValue = 7500, currency = "INR")

        assertTrue(decision.eligible)
        assertContains(sdk.getBannerText("merchant_123", 7500), "Unlock 10% cashback")
        assertContains(decision.widgetUrl, "userId=android_user")
    }
}
