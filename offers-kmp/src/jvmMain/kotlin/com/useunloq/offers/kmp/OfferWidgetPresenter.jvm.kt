package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferDecision

actual class OfferWidgetPresenter actual constructor() {
    actual fun present(
        decision: OfferDecision,
        hostContext: OfferWidgetHostContext
    ): OfferWidgetPresentation {
        return OfferWidgetPresentation(
            platformShell = "JVM demo modal shell",
            widgetUrl = decision.widgetUrl,
            summary = "${hostContext.screenName} on ${hostContext.hostId} -> ${decision.rewardText}"
        )
    }
}
