plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("com.useunloq.offers.merchant")
}

val iosDerivedDataPath = providers.gradleProperty("iosDerivedData")
    .orElse(providers.environmentVariable("IOS_DERIVED_DATA"))
    .orElse("/tmp/unloq-kmp-ios-derived-data")
    .get()

kotlin {
    androidTarget()
    val iosArm64Target = iosArm64()
    val iosSimulatorArm64Target = iosSimulatorArm64()

    listOf(iosArm64Target, iosSimulatorArm64Target).forEach { target ->
        target.binaries.framework {
            baseName = "KmpMerchantShared"
        }
    }


    sourceSets {
        commonMain.dependencies {
            // offers-kmp is added automatically by the com.useunloq.offers.merchant plugin
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "merchant.demo.kmp"
    compileSdk = 35

    defaultConfig {
        applicationId = "merchant.demo.kmp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.material:material:1.12.0")
}
