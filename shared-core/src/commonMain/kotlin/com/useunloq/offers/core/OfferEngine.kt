package com.useunloq.offers.core

class OfferEngine(
    private val platformInfo: PlatformInfo = DefaultPlatformInfo()
) {
    private var config: OfferSdkConfig? = null
    private var user: OfferUser? = null
    private var attribution: OfferAttribution? = null
    private val events = mutableListOf<OfferEvent>()

    fun initialize(config: OfferSdkConfig) {
        this.config = config
    }

    fun setUser(user: OfferUser?) {
        this.user = user
    }

    fun setAttribution(attribution: OfferAttribution?) {
        this.attribution = attribution
    }

    fun emitEvent(event: OfferEvent) {
        events += event
    }

    fun evaluate(cartValue: Long, currency: String): OfferDecision {
        val activeConfig = requireNotNull(config) {
            "OfferEngine must be initialized before evaluate()"
        }
        val activeUser = requireNotNull(user) {
            "OfferEngine requires a user before evaluate()"
        }

        val eligible = cartValue >= 5000
        val rewardText = if (eligible) {
            "Unlock 10% cashback"
        } else {
            "Add ${(5000 - cartValue).coerceAtLeast(0)} more to unlock cashback"
        }
        val attributionValue = attribution?.source ?: "direct"
        val widgetUrl = buildString {
            append(activeConfig.widgetBaseUrl)
            append("?merchantId=")
            append(activeConfig.merchantId)
            append("&userId=")
            append(activeUser.id)
            append("&eligible=")
            append(eligible)
            append("&source=")
            append(attributionValue)
            append("&platform=")
            append(platformInfo.name())
        }
        val debugSummary = buildString {
            append(activeConfig.environment.name)
            append(" merchant=")
            append(activeConfig.merchantId)
            append(" user=")
            append(activeUser.id)
            append(" tier=")
            append(activeUser.loyaltyTier)
            append(" currency=")
            append(currency)
            append(" cartValue=")
            append(cartValue)
            append(" events=")
            append(events.size)
        }

        return OfferDecision(
            title = "UNLOQ Offer",
            rewardText = rewardText,
            eligible = eligible,
            widgetUrl = widgetUrl,
            debugSummary = debugSummary,
            platform = platformInfo.name()
        )
    }
}
