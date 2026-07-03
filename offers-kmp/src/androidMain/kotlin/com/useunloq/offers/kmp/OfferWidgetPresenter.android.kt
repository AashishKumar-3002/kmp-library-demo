package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferDecision

actual class OfferWidgetPresenter actual constructor() {
    actual fun present(
        decision: OfferDecision,
        hostContext: OfferWidgetHostContext
    ): OfferWidgetPresentation {
        return OfferWidgetPresentation(
            platformShell = "Android bottom sheet + WebView",
            widgetUrl = decision.widgetUrl,
            summary = "${hostContext.screenName} on ${hostContext.hostId} -> ${decision.rewardText}"
        )
    }
}
