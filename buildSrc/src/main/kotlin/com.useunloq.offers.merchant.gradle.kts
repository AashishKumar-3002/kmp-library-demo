import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val iosDerivedDataPath = project.providers.gradleProperty("iosDerivedData")
    .orElse(project.providers.environmentVariable("IOS_DERIVED_DATA"))
    .orElse("/tmp/unloq-kmp-ios-derived-data")
    .get()

fun configureNativeIosLinking(target: KotlinNativeTarget, sdkFolder: String) {
    val repoRoot = project.rootDir.absolutePath
    val isSimulator = sdkFolder.contains("simulator")
    
    val simulatorObj = "$repoRoot/native-ios-wrapper/archives/NativeIosWrapperDemo-iphonesimulator.xcarchive/Products/Users/aashishkumar/Objects/NativeIosWrapperDemo.o"
    val deviceObj = "$repoRoot/native-ios-wrapper/archives/NativeIosWrapperDemo-iphoneos.xcarchive/Products/Users/aashishkumar/Objects/NativeIosWrapperDemo.o"
    val wrapperObj = if (isSimulator) simulatorObj else deviceObj
    
    val frameworkFolder = if (isSimulator) "ios-arm64_x86_64-simulator" else "ios-arm64"
    val unloqOffersCorePath = "$repoRoot/shared-core/build/XCFrameworks/release/UnloqOffersCore.xcframework/$frameworkFolder"
    
    target.binaries.all {
        linkerOpts(
            wrapperObj,
            "-F$unloqOffersCorePath",
            "-framework", "UnloqOffersCore"
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
