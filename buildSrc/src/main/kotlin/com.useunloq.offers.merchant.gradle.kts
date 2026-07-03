import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val iosDerivedDataPath = project.providers.gradleProperty("iosDerivedData")
    .orElse(project.providers.environmentVariable("IOS_DERIVED_DATA"))
    .orElse("/tmp/unloq-kmp-ios-derived-data")
    .get()

fun configureNativeIosLinking(target: KotlinNativeTarget, sdkFolder: String) {
    target.binaries.all {
        linkerOpts(
            "-F$iosDerivedDataPath/Build/Products/$sdkFolder/PackageFrameworks",
            "-framework", "NativeIosWrapperDemo",
            "-framework", "UnloqOffersCore",
            "-rpath", "$iosDerivedDataPath/Build/Products/$sdkFolder/PackageFrameworks"
        )
    }
}

project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
    val kotlin = project.extensions.getByType(KotlinMultiplatformExtension::class.java)
    
    // Add commonMain dependency on offers-kmp
    kotlin.sourceSets.named("commonMain").configure {
        dependencies {
            implementation(project(":offers-kmp"))
        }
    }

    // Link the native iOS frameworks to any iOS targets
    kotlin.targets.withType(KotlinNativeTarget::class.java).configureEach {
        if (name == "iosSimulatorArm64") {
            configureNativeIosLinking(this, "Debug-iphonesimulator")
        } else if (name == "iosArm64") {
            configureNativeIosLinking(this, "Debug-iphoneos")
        }
    }
}
