package com.useunloq.offers.kmp

enum class OfferEnvironment {
    QA,
    PROD
}

data class OfferWidgetHostContext(
    val screenName: String,
    val hostId: String
)

data class OfferWidgetPresentation(
    val platformShell: String,
    val widgetUrl: String,
    val summary: String
)
