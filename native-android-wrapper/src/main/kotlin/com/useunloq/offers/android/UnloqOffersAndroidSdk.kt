package com.useunloq.offers.android

import android.view.LayoutInflater
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.useunloq.offers.core.OfferAttribution
import com.useunloq.offers.core.OfferDecision
import com.useunloq.offers.core.OfferEnvironment
import com.useunloq.offers.core.OfferEvent
import com.useunloq.offers.core.OfferSdkConfig
import com.useunloq.offers.core.OfferUser
import com.useunloq.offers.core.UnloqOffersCoreClient

data class AndroidOfferWidgetPresentation(
    val platformShell: String,
    val widgetUrl: String,
    val summary: String
)

class UnloqOffersAndroidSdk {
    private val client = UnloqOffersCoreClient()

    fun initialize(
        merchantId: String,
        environment: String,
        widgetBaseUrl: String
    ) {
        client.initialize(
            OfferSdkConfig(
                merchantId = merchantId,
                environment = environment.toCoreEnvironment(),
                widgetBaseUrl = widgetBaseUrl
            )
        )
    }

    fun setUser(id: String, loyaltyTier: String) {
        client.setUser(OfferUser(id = id, loyaltyTier = loyaltyTier))
    }

    fun setAttribution(source: String, campaign: String) {
        client.setAttribution(OfferAttribution(source = source, campaign = campaign))
    }

    fun emitEvent(name: String, value: String) {
        client.emitEvent(OfferEvent(name = name, value = value))
    }

    fun evaluate(cartValue: Long, currency: String): OfferDecision {
        return client.evaluate(cartValue = cartValue, currency = currency)
    }

    fun prepareWidget(
        screenName: String,
        hostId: String,
        cartValue: Long,
        currency: String
    ): AndroidOfferWidgetPresentation {
        val decision = evaluate(cartValue = cartValue, currency = currency)
        return AndroidOfferWidgetPresentation(
            platformShell = "Android SDK bottom sheet + WebView",
            widgetUrl = decision.widgetUrl,
            summary = "$screenName on $hostId -> ${decision.rewardText}"
        )
    }

    fun showWidget(
        activity: FragmentActivity,
        screenName: String,
        hostId: String,
        cartValue: Long,
        currency: String
    ): AndroidOfferWidgetPresentation {
        val presentation = prepareWidget(
            screenName = screenName,
            hostId = hostId,
            cartValue = cartValue,
            currency = currency
        )
        renderWidget(activity = activity, presentation = presentation)
        return presentation
    }

    fun getBannerText(merchantId: String, cartValue: Long): String {
        val result = client.evaluate(cartValue = cartValue, currency = "INR")

        return "$merchantId -> ${result.title}: ${result.rewardText}"
    }

    private fun renderWidget(
        activity: FragmentActivity,
        presentation: AndroidOfferWidgetPresentation
    ) {
        val webView = LayoutInflater.from(activity)
            .inflate(android.R.layout.simple_list_item_1, null)
            .let {
                WebView(activity).apply {
                    settings.javaScriptEnabled = true
                    loadUrl(presentation.widgetUrl)
                }
            }

        BottomSheetDialog(activity).apply {
            setContentView(webView)
            show()
        }
    }

    private fun String.toCoreEnvironment(): OfferEnvironment {
        return when (uppercase()) {
            "PROD", "PRODUCTION" -> OfferEnvironment.PROD
            else -> OfferEnvironment.QA
        }
    }
}
