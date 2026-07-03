package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferDecision

actual class OfferWidgetPresenter actual constructor() {
    actual fun present(
        decision: OfferDecision,
        hostContext: OfferWidgetHostContext
    ): OfferWidgetPresentation {
        return OfferWidgetPresentation(
            platformShell = "iOS sheet + WKWebView",
            widgetUrl = decision.widgetUrl,
            summary = "${hostContext.screenName} on ${hostContext.hostId} -> ${decision.rewardText}"
        )
    }
}
