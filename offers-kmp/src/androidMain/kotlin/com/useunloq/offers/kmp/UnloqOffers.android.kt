package com.useunloq.offers.kmp

import androidx.fragment.app.FragmentActivity

fun UnloqOffers.showWidget(
    activity: FragmentActivity,
    hostContext: OfferWidgetHostContext,
    cartValue: Long,
    currency: String
): OfferWidgetPresentation {
    val presentation = showWidget(
        hostContext = hostContext,
        cartValue = cartValue,
        currency = currency
    )
    AndroidOfferWidgetRenderer().render(activity = activity, presentation = presentation)
    return presentation
}
