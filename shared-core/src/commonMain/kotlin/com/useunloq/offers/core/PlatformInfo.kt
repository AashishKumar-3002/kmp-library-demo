package com.useunloq.offers.core

interface PlatformInfo {
    fun name(): String
}

expect class DefaultPlatformInfo() : PlatformInfo {
    override fun name(): String
}

