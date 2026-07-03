package com.useunloq.offers.kmp

import kotlinx.cinterop.ExperimentalForeignApi
import nativeios.UNQOffersBridge

@OptIn(ExperimentalForeignApi::class)
actual class UnloqOffers actual constructor() {
    private val iosSdk = UNQOffersBridge()

    actual fun initialize(
        merchantId: String,
        environment: OfferEnvironment,
        widgetBaseUrl: String
    ) {
        iosSdk.initializeWithMerchantId(
            merchantId = merchantId,
            widgetBaseUrl = widgetBaseUrl
        )
    }

    actual fun setUser(id: String, loyaltyTier: String) {
        iosSdk.setUserWithId(id = id, loyaltyTier = loyaltyTier)
    }

    actual fun setAttribution(source: String, campaign: String) {
        iosSdk.setAttributionWithSource(source = source, campaign = campaign)
    }

    actual fun emitEvent(name: String, value: String) {
        iosSdk.emitEventWithName(name = name, value = value)
    }

    actual fun showWidget(
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation {
        return OfferWidgetPresentation(
            platformShell = "iOS SDK sheet + WKWebView",
            widgetUrl = iosSdk.widgetUrlWithCartValue(cartValue),
            summary = iosSdk.widgetPresentationSummaryWithScreenName(
                screenName = hostContext.screenName,
                hostId = hostContext.hostId,
                cartValue = cartValue
            )
        )
    }
}
