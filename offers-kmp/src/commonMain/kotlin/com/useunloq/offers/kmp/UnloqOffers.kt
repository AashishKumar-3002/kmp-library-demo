package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferAttribution
import com.useunloq.offers.core.OfferDecision
import com.useunloq.offers.core.OfferEnvironment
import com.useunloq.offers.core.OfferEvent
import com.useunloq.offers.core.OfferSdkConfig
import com.useunloq.offers.core.OfferUser
import com.useunloq.offers.core.UnloqOffersCoreClient

class UnloqOffers(
    private val client: UnloqOffersCoreClient = UnloqOffersCoreClient(),
    private val presenter: OfferWidgetPresenter = OfferWidgetPresenter()
) {
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

    fun showWidget(
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation {
        val decision = evaluate(cartValue = cartValue, currency = currency)
        return presenter.present(decision = decision, hostContext = hostContext)
    }
}
