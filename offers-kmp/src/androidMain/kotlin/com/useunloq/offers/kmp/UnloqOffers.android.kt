package com.useunloq.offers.kmp

import com.useunloq.offers.android.UnloqOffersAndroidSdk
import com.useunloq.offers.android.AndroidOfferWidgetPresentation

actual class UnloqOffers actual constructor() {
    private val androidSdk = UnloqOffersAndroidSdk()
    var context: android.content.Context? = null

    actual fun initialize(
        merchantId: String,
        environment: OfferEnvironment,
        widgetBaseUrl: String
    ) {
        androidSdk.initialize(
            merchantId = merchantId,
            environment = environment.name,
            widgetBaseUrl = widgetBaseUrl
        )
    }

    actual fun setUser(id: String, loyaltyTier: String) {
        androidSdk.setUser(id = id, loyaltyTier = loyaltyTier)
    }

    actual fun setAttribution(source: String, campaign: String) {
        androidSdk.setAttribution(source = source, campaign = campaign)
    }

    actual fun emitEvent(name: String, value: String) {
        androidSdk.emitEvent(name = name, value = value)
    }

    actual fun showWidget(
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation {
        val safeContext = context
        return if (safeContext != null) {
            val presentation = androidSdk.showWidget(
                context = safeContext,
                screenName = hostContext.screenName,
                hostId = hostContext.hostId,
                cartValue = cartValue,
                currency = currency
            )
            presentation.toKmpPresentation()
        } else {
            // Fallback to prepareWidget if no context is available
            val presentation = androidSdk.prepareWidget(
                screenName = hostContext.screenName,
                hostId = hostContext.hostId,
                cartValue = cartValue,
                currency = currency
            )
            presentation.toKmpPresentation()
        }
    }
}

private fun com.useunloq.offers.android.AndroidOfferWidgetPresentation.toKmpPresentation(): OfferWidgetPresentation {
    return OfferWidgetPresentation(
        platformShell = platformShell,
        widgetUrl = widgetUrl,
        summary = summary
    )
}
