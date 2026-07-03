package merchant.demo

import kotlin.test.Test
import kotlin.test.assertContains

class OfferUsageTest {
    @Test
    fun `merchant app can use shared core from common code`() {
        val usage = OfferUsage()
        usage.setup()

        val text = usage.renderOffer()
        val widgetText = usage.showWidget()

        assertContains(text, "Unlock 10% cashback")
        assertContains(text, "merchantId=merchant_123")
        assertContains(widgetText, "widget")
    }
}
