package merchant.demo

class KmpMerchantBridge {
    private val offers = OfferUsage()

    fun title(): String = "UNLOQ KMP Merchant App"

    fun subtitle(): String =
        "This iOS app uses kmp-merchant-app shared code, which depends on offers-kmp. " +
            "The iOS offers-kmp target delegates to the native Swift SDK wrapper."

    fun showOfferWidget(): String {
        offers.setup()
        return offers.showWidget()
    }
}
