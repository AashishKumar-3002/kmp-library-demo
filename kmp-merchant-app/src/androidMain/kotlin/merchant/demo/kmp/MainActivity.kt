package merchant.demo.kmp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.useunloq.offers.kmp.OfferWidgetHostContext
import merchant.demo.OfferUsage
import merchant.demo.kmp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val offerUsage = OfferUsage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The initialization logic is now shared entirely in commonMain!
        offerUsage.setup()

        binding.showWidgetButton.setOnClickListener {
            // We use the common offers instance, but call the Android-specific showWidget extension
            // that accepts the Activity context, because Android UI must be tied to an Activity.
            val presentation = offerUsage.offers.showWidget(
                activity = this,
                hostContext = OfferWidgetHostContext(
                    screenName = "Checkout",
                    hostId = "kmp_merchant_android"
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
