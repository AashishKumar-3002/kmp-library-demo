package com.useunloq.offers.android

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
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
        context: android.content.Context,
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
        renderWidget(context = context, presentation = presentation)
        return presentation
    }

    fun getBannerText(merchantId: String, cartValue: Long): String {
        val result = client.evaluate(cartValue = cartValue, currency = "INR")

        return "$merchantId -> ${result.title}: ${result.rewardText}"
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderWidget(
        context: android.content.Context,
        presentation: AndroidOfferWidgetPresentation
    ) {
        lateinit var dialog: BottomSheetDialog
        val webView = WebView(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.javaScriptEnabled = true
            addJavascriptInterface(
                OfferWidgetActionBridge(context = context) {
                    dialog.dismiss()
                },
                "UnloqAndroid"
            )
            loadDataWithBaseURL(
                presentation.widgetUrl,
                buildDemoWidgetHtml(presentation),
                "text/html",
                "utf-8",
                null
            )
        }

        dialog = BottomSheetDialog(context).apply {
            setContentView(webView)
            findViewById<android.view.View>(com.google.android.material.R.id.design_bottom_sheet)?.setBackgroundResource(android.R.color.transparent)
            show()
        }
    }

    private fun buildDemoWidgetHtml(presentation: AndroidOfferWidgetPresentation): String {
        return """
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
            </head>
            <body style="margin:0;font-family:sans-serif;background:transparent;color:#172033;">
                <div style="background:#fff8df; border-top-left-radius:24px; border-top-right-radius:24px; padding:24px; min-height: 100vh;">
                    <div style="width:36px;height:4px;background:#cccccc;border-radius:2px;margin:0 auto 20px;"></div>
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
                    <button onclick="UnloqAndroid.continueWithOffer()" style="margin-top:20px;width:100%;border:0;border-radius:999px;padding:14px;background:#4f3b7f;color:white;font-size:16px;font-weight:700;">
                        Continue with offer
                    </button>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    private class OfferWidgetActionBridge(
        private val context: android.content.Context,
        private val dismissWidget: () -> Unit
    ) {
        @JavascriptInterface
        fun continueWithOffer() {
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    "Offer accepted from native Android SDK",
                    Toast.LENGTH_SHORT
                ).show()
                dismissWidget()
            }
        }
    }

    private fun String.toCoreEnvironment(): OfferEnvironment {
        return when (uppercase()) {
            "PROD", "PRODUCTION" -> OfferEnvironment.PROD
            else -> OfferEnvironment.QA
        }
    }
}
