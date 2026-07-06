package com.example.kmpmerchantcomposeapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.useunloq.offers.kmp.OfferEnvironment
import com.useunloq.offers.kmp.OfferWidgetHostContext
import com.useunloq.offers.kmp.UnloqCampaignLandingWidget
import com.useunloq.offers.kmp.UnloqOffers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember

@Composable
fun App() {
    MaterialTheme {
        // Initialize the UNLOQ SDK
        val offers = remember { 
            UnloqOffers().apply {
                initialize(
                    merchantId = "merchant_123",
                    environment = OfferEnvironment.QA,
                    widgetBaseUrl = "https://qa-sdk.useunloq.com/widget"
                )
                setUser(id = "user_456", loyaltyTier = "gold")
            }
        }

        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Compose: $greeting")
                    
                    // Render the UNLOQ shared widget (inline View wrapper)
                    UnloqCampaignLandingWidget(placement = "campaign_landing")
                }
            }
        }
    }
}