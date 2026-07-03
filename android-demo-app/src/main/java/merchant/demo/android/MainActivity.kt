package merchant.demo.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.useunloq.offers.core.OfferEnvironment
import com.useunloq.offers.kmp.OfferWidgetHostContext
import com.useunloq.offers.kmp.UnloqOffers
import com.useunloq.offers.kmp.showWidget
import merchant.demo.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val offers = UnloqOffers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        offers.initialize(
            merchantId = "merchant_123",
            environment = OfferEnvironment.QA,
            widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
        )
        offers.setUser(id = "android_demo_user", loyaltyTier = "gold")
        offers.setAttribution(source = "branch", campaign = "android-demo")
        offers.emitEvent(name = "checkout_started", value = "1")

        binding.showWidgetButton.setOnClickListener {
            val presentation = offers.showWidget(
                activity = this,
                hostContext = OfferWidgetHostContext(
                    screenName = "Checkout",
                    hostId = "android_demo_activity"
                ),
                cartValue = 7500,
                currency = "INR"
            )

            binding.resultText.text = buildString {
                appendLine("Platform shell: ${presentation.platformShell}")
                appendLine("Summary: ${presentation.summary}")
                append("Widget URL: ${presentation.widgetUrl}")
            }
        }
    }
}
