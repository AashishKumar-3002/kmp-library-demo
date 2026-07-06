package com.example.kmpmerchantcomposeapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform