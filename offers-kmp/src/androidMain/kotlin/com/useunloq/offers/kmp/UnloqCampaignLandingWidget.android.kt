package com.useunloq.offers.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.useunloq.offers.android.UnloqOffersAndroidSdk

@Composable
actual fun UnloqCampaignLandingWidget(placement: String) {
    val composeContext = LocalContext.current
    LaunchedEffect(placement) {
        UnloqOffersAndroidSdk().showWidget(
            context = composeContext,
            screenName = placement,
            hostId = "host_1",
            cartValue = 1000L,
            currency = "INR"
        )
    }
}
