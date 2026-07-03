package com.useunloq.offers.kmp

expect class UnloqOffers() {
    fun initialize(
        merchantId: String,
        environment: OfferEnvironment,
        widgetBaseUrl: String
    )

    fun setUser(id: String, loyaltyTier: String)

    fun setAttribution(source: String, campaign: String)

    fun emitEvent(name: String, value: String)

    fun showWidget(
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation
}
