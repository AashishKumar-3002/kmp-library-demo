package com.useunloq.offers.android

import android.annotation.SuppressLint
import android.graphics.Color
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderWidget(
        activity: FragmentActivity,
        presentation: AndroidOfferWidgetPresentation
    ) {
        val webView = WebView(activity).apply {
            setBackgroundColor(Color.WHITE)
            settings.javaScriptEnabled = true
            loadDataWithBaseURL(
                presentation.widgetUrl,
                buildDemoWidgetHtml(presentation),
                "text/html",
                "utf-8",
                null
            )
        }

        BottomSheetDialog(activity).apply {
            setContentView(webView)
            show()
        }
    }

    private fun buildDemoWidgetHtml(presentation: AndroidOfferWidgetPresentation): String {
        return """
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
            </head>
            <body style="margin:0;font-family:sans-serif;background:#fff8df;color:#172033;">
                <div style="padding:24px;">
                    <div style="font-size:12px;letter-spacing:0.12em;text-transform:uppercase;color:#7b5d00;">
                        UNLOQ Native Android SDK
                    </div>
                    <h1 style="font-size:28px;line-height:1.1;margin:12px 0 8px;">
                        Offer widget shell
                    </h1>
                    <p style="font-size:16px;line-height:1.45;margin:0 0 18px;">
                        ${presentation.summary}
                    </p>
                    <div style="border-radius:18px;background:#ffffff;padding:16px;border:1px solid #ecd98a;">
                        <strong>Generated widget URL</strong>
                        <div style="font-size:12px;line-height:1.4;word-break:break-word;margin-top:8px;">
                            ${presentation.widgetUrl}
                        </div>
                    </div>
                    <button style="margin-top:20px;width:100%;border:0;border-radius:999px;padding:14px;background:#4f3b7f;color:white;font-size:16px;font-weight:700;">
                        Continue with offer
                    </button>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    private fun String.toCoreEnvironment(): OfferEnvironment {
        return when (uppercase()) {
            "PROD", "PRODUCTION" -> OfferEnvironment.PROD
            else -> OfferEnvironment.QA
        }
    }
}
