plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

val compose = extensions.getByType<org.jetbrains.compose.ComposeExtension>().dependencies

val iosDerivedDataPath = providers.gradleProperty("iosDerivedData")
    .orElse(providers.environmentVariable("IOS_DERIVED_DATA"))
    .orElse("/tmp/unloq-kmp-ios-derived-data")
    .get()

kotlin {
    androidTarget()
    val iosArm64Target = iosArm64()
    val iosSimulatorArm64Target = iosSimulatorArm64()

    fun configureNativeIosWrapperInterop(
        target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget,
        sdkFolder: String,
        moduleMapFolder: String
    ) {
        target.binaries.all {
            linkerOpts(
                "$iosDerivedDataPath/Build/Products/$sdkFolder/NativeIosWrapperDemo.o",
                "-F$iosDerivedDataPath/Build/Products/$sdkFolder/PackageFrameworks",
                "-framework",
                "UnloqOffersCore",
                "-rpath",
                "$iosDerivedDataPath/Build/Products/$sdkFolder/PackageFrameworks"
            )
        }

        target.compilations.getByName("main") {
            cinterops {
                val nativeIosWrapperDemo by creating {
                    defFile(project.file("src/nativeInterop/cinterop/NativeIosWrapperDemo.def"))
                    packageName("nativeios")
                    compilerOpts(
                        "-I$iosDerivedDataPath/Build/Intermediates.noindex/$moduleMapFolder"
                    )
                }
            }
        }
    }

    configureNativeIosWrapperInterop(
        target = iosSimulatorArm64Target,
        sdkFolder = "Debug-iphonesimulator",
        moduleMapFolder = "GeneratedModuleMaps-iphonesimulator"
    )
    configureNativeIosWrapperInterop(
        target = iosArm64Target,
        sdkFolder = "Debug-iphoneos",
        moduleMapFolder = "GeneratedModuleMaps-iphoneos"
    )

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }

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
