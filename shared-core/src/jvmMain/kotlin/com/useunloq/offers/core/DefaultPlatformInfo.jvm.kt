package com.useunloq.offers.core

actual class DefaultPlatformInfo actual constructor() : PlatformInfo {
    actual override fun name(): String = "JVM"
}
