plugins {
    kotlin("multiplatform")
    id("com.android.library")
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

        compilations.getByName("main") {
            cinterops {
                val nativeIosWrapperDemo by creating {
                    defFile(project.file("src/nativeInterop/cinterop/NativeIosWrapperDemo.def"))
                    packageName("nativeios")
                    compilerOpts(
                        "-I$iosDerivedDataPath/Build/Intermediates.noindex/GeneratedModuleMaps-iphonesimulator"
                    )
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":native-android-wrapper"))
            api("androidx.fragment:fragment-ktx:1.8.2")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.useunloq.offers.kmp"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
