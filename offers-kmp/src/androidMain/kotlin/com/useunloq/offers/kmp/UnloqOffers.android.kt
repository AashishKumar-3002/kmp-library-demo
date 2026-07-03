package com.useunloq.offers.kmp

import androidx.fragment.app.FragmentActivity
import com.useunloq.offers.android.UnloqOffersAndroidSdk

actual class UnloqOffers actual constructor() {
    private val androidSdk = UnloqOffersAndroidSdk()

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
        val presentation = androidSdk.prepareWidget(
            screenName = hostContext.screenName,
            hostId = hostContext.hostId,
            cartValue = cartValue,
            currency = currency
        )
        return presentation.toKmpPresentation()
    }

    fun showWidget(
        activity: FragmentActivity,
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation {
        val presentation = androidSdk.showWidget(
            activity = activity,
            screenName = hostContext.screenName,
            hostId = hostContext.hostId,
            cartValue = cartValue,
            currency = currency
        )
        return presentation.toKmpPresentation()
    }
}

private fun com.useunloq.offers.android.AndroidOfferWidgetPresentation.toKmpPresentation(): OfferWidgetPresentation {
    return OfferWidgetPresentation(
        platformShell = platformShell,
        widgetUrl = widgetUrl,
        summary = summary
    )
}
