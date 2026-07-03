package com.useunloq.offers.core

enum class OfferEnvironment {
    QA,
    PROD
}

class OfferSdkConfig(
    val merchantId: String,
    val environment: OfferEnvironment,
    val widgetBaseUrl: String
)

class OfferUser(
    val id: String,
    val loyaltyTier: String
)

class OfferAttribution(
    val source: String,
    val campaign: String
)

class OfferEvent(
    val name: String,
    val value: String
)

class OfferDecision(
    val title: String,
    val rewardText: String,
    val eligible: Boolean,
    val widgetUrl: String,
    val debugSummary: String,
    val platform: String
)
