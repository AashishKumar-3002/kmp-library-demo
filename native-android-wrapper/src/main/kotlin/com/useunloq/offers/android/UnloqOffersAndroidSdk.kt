package com.useunloq.offers.android

import com.useunloq.offers.core.OfferAttribution
import com.useunloq.offers.core.OfferDecision
import com.useunloq.offers.core.OfferEnvironment
import com.useunloq.offers.core.OfferEvent
import com.useunloq.offers.core.OfferSdkConfig
import com.useunloq.offers.core.OfferUser
import com.useunloq.offers.core.UnloqOffersCoreClient

class UnloqOffersAndroidSdk {
    private val client = UnloqOffersCoreClient()

    fun initialize(
        merchantId: String,
        environment: OfferEnvironment,
        widgetBaseUrl: String
    ) {
        client.initialize(
            OfferSdkConfig(
                merchantId = merchantId,
                environment = environment,
                widgetBaseUrl = widgetBaseUrl
            )
        )
    }

    fun setUser(id: String, loyaltyTier: String) {
        client.setUser(OfferUser(id = id, loyaltyTier = loyaltyTier))
    }

    fun setAttribution(source: String, campaign: String) {
        client.setAttribution(OfferAttribution(source = source, campaign = campaign))
    }

    fun emitEvent(name: String, value: String) {
        client.emitEvent(OfferEvent(name = name, value = value))
    }

    fun evaluate(cartValue: Long, currency: String): OfferDecision {
        return client.evaluate(cartValue = cartValue, currency = currency)
    }

    fun getBannerText(merchantId: String, cartValue: Long): String {
        val result = client.evaluate(cartValue = cartValue, currency = "INR")

        return "$merchantId -> ${result.title}: ${result.rewardText}"
    }
}
