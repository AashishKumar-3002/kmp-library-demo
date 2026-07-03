import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

group = "com.useunloq"
version = "1.0.0"

kotlin {
    jvm()

    androidTarget {
        publishLibraryVariants("release")
    }

    val iosArm64 = iosArm64()
    val iosSimulatorArm64 = iosSimulatorArm64()
    val macosArm64 = macosArm64()
    val iosX64 = iosX64()
    val xcf = XCFramework("UnloqOffersCore")

    listOf(
        iosArm64,
        iosSimulatorArm64,
        macosArm64,
        iosX64
    ).forEach { target ->
        target.binaries.framework {
            baseName = "UnloqOffersCore"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Put shared dependencies here, for example Ktor, kotlinx.serialization, etc.
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.useunloq.offers.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
