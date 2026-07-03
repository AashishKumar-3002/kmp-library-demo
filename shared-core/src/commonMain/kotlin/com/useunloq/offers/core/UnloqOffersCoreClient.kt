package com.useunloq.offers.core

class UnloqOffersCoreClient(
    private val engine: OfferEngine = OfferEngine()
) {
    constructor() : this(OfferEngine())

    fun initialize(config: OfferSdkConfig) {
        engine.initialize(config)
    }

    fun setUser(user: OfferUser?) {
        engine.setUser(user)
    }

    fun setAttribution(attribution: OfferAttribution?) {
        engine.setAttribution(attribution)
    }

    fun emitEvent(event: OfferEvent) {
        engine.emitEvent(event)
    }

    fun evaluate(cartValue: Long, currency: String): OfferDecision {
        return engine.evaluate(cartValue = cartValue, currency = currency)
    }
}
