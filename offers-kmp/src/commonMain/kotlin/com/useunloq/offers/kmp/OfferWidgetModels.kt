package com.useunloq.offers.kmp

data class OfferWidgetHostContext(
    val screenName: String,
    val hostId: String
)

data class OfferWidgetPresentation(
    val platformShell: String,
    val widgetUrl: String,
    val summary: String
)
