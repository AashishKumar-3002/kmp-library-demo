package com.useunloq.offers.kmp

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class AndroidOfferWidgetRenderer {
    @SuppressLint("SetJavaScriptEnabled")
    fun render(
        activity: FragmentActivity,
        presentation: OfferWidgetPresentation
    ) {
        val webView = WebView(activity).apply {
            setBackgroundColor(Color.WHITE)
            settings.javaScriptEnabled = true
            loadDataWithBaseURL(
                presentation.widgetUrl,
                buildHtml(presentation),
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

    private fun buildHtml(presentation: OfferWidgetPresentation): String {
        return """
            <html>
            <body style="font-family: -apple-system, BlinkMacSystemFont, sans-serif; padding: 24px; color: #152033;">
                <div style="padding: 18px; border-radius: 18px; background: linear-gradient(135deg, #fff4d6, #f6fbff); border: 1px solid #dce7f3;">
                    <div style="font-size: 12px; letter-spacing: 0.08em; text-transform: uppercase; color: #6a778b;">UNLOQ Demo Widget</div>
                    <h2 style="margin: 12px 0 8px 0;">${presentation.platformShell}</h2>
                    <p style="margin: 0 0 12px 0;">${presentation.summary}</p>
                    <div style="padding: 12px; background: white; border-radius: 12px; font-size: 12px; word-break: break-word;">
                        ${presentation.widgetUrl}
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
