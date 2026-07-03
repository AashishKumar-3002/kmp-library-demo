package com.useunloq.offers.kmp

actual class UnloqOffers actual constructor() {
    actual fun initialize(
        merchantId: String,
        environment: OfferEnvironment,
        widgetBaseUrl: String
    ) {
        nativeIosSdkRequired()
    }

    actual fun setUser(id: String, loyaltyTier: String) {
        nativeIosSdkRequired()
    }

    actual fun setAttribution(source: String, campaign: String) {
        nativeIosSdkRequired()
    }

    actual fun emitEvent(name: String, value: String) {
        nativeIosSdkRequired()
    }

    actual fun showWidget(
        hostContext: OfferWidgetHostContext,
        cartValue: Long,
        currency: String
    ): OfferWidgetPresentation {
        nativeIosSdkRequired()
    }

    private fun nativeIosSdkRequired(): Nothing {
        error(
            "The device iOS KMP wrapper target needs a device NativeIosWrapperDemo.framework " +
                "or XCFramework slice wired through Kotlin/Native cinterop."
        )
    }
}
