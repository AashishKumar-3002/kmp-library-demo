plugins {
    kotlin("multiplatform")
    id("com.android.application")
}

val iosDerivedDataPath = providers.gradleProperty("iosDerivedData")
    .orElse(providers.environmentVariable("IOS_DERIVED_DATA"))
    .orElse("/tmp/unloq-kmp-ios-derived-data")
    .get()

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64 {
        binaries.all {
            linkerOpts(
                "-F$iosDerivedDataPath/Build/Products/Debug-iphonesimulator/PackageFrameworks",
                "-framework",
                "NativeIosWrapperDemo",
                "-framework",
                "UnloqOffersCore",
                "-rpath",
                "$iosDerivedDataPath/Build/Products/Debug-iphonesimulator/PackageFrameworks"
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":offers-kmp"))
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
