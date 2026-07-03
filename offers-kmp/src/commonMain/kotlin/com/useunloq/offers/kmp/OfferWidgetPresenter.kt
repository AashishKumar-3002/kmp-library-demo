package com.useunloq.offers.kmp

import com.useunloq.offers.core.OfferDecision

expect class OfferWidgetPresenter() {
    fun present(
        decision: OfferDecision,
        hostContext: OfferWidgetHostContext
    ): OfferWidgetPresentation
}
