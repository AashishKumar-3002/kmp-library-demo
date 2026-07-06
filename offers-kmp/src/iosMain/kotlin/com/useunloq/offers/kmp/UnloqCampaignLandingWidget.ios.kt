package com.useunloq.offers.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun UnloqCampaignLandingWidget(placement: String) {
    LaunchedEffect(placement) {
        val offers = UnloqOffers()
        offers.showWidget(
            hostContext = OfferWidgetHostContext(
                screenName = placement,
                hostId = "host_1"
            ),
            cartValue = 1000L,
            currency = "INR"
        )
    }
}
