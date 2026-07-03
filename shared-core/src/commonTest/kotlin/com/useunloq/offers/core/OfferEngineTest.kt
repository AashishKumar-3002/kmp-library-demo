package com.useunloq.offers.core

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OfferEngineTest {
    @Test
    fun `evaluate requires initialization and user`() {
        val engine = OfferEngine()

        assertFailsWith<IllegalArgumentException> {
            engine.evaluate(cartValue = 7500, currency = "INR")
        }

        engine.initialize(
            OfferSdkConfig(
                merchantId = "merchant_123",
                environment = OfferEnvironment.QA,
                widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
            )
        )

        assertFailsWith<IllegalArgumentException> {
            engine.evaluate(cartValue = 7500, currency = "INR")
        }
    }

    @Test
    fun `evaluate builds widget url and eligibility from shared state`() {
        val engine = OfferEngine(platformInfo = object : PlatformInfo {
            override fun name(): String = "Test"
        })

        engine.initialize(
            OfferSdkConfig(
                merchantId = "merchant_123",
                environment = OfferEnvironment.QA,
                widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
            )
        )
        engine.setUser(OfferUser(id = "user_42", loyaltyTier = "gold"))
        engine.setAttribution(OfferAttribution(source = "branch", campaign = "monsoon"))
        engine.emitEvent(OfferEvent(name = "checkout_started", value = "1"))

        val decision = engine.evaluate(cartValue = 7500, currency = "INR")

        assertTrue(decision.eligible)
        assertEquals("UNLOQ Offer", decision.title)
        assertContains(decision.widgetUrl, "merchantId=merchant_123")
        assertContains(decision.widgetUrl, "userId=user_42")
        assertContains(decision.widgetUrl, "source=branch")
        assertContains(decision.debugSummary, "events=1")
        assertEquals("Test", decision.platform)
    }

    @Test
    fun `evaluate returns non eligible copy below threshold`() {
        val engine = OfferEngine(platformInfo = object : PlatformInfo {
            override fun name(): String = "Test"
        })

        engine.initialize(
            OfferSdkConfig(
                merchantId = "merchant_123",
                environment = OfferEnvironment.QA,
                widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
            )
        )
        engine.setUser(OfferUser(id = "user_42", loyaltyTier = "silver"))

        val decision = engine.evaluate(cartValue = 4200, currency = "INR")

        assertFalse(decision.eligible)
        assertContains(decision.rewardText, "800")
    }
}
