# KMP Library Demo

This repo validates the final SDK split:

```text
shared-core
  -> owns core KMP business logic

native-android-wrapper
  -> depends on shared-core
  -> owns Android SDK implementation and Android UI

native-ios-wrapper
  -> depends on shared-core/UnloqOffersCore.xcframework
  -> owns Swift SDK implementation and iOS UI/bridge surface

offers-kmp
  -> commonMain is only a facade
  -> androidMain delegates to native-android-wrapper
  -> iosSimulatorArm64Main delegates to native-ios-wrapper through ObjC/cinterop

kmp-merchant-app
  -> sample merchant app consuming offers-kmp
  -> builds Android APK
  -> validates iOS simulator path through KMP tests
```

The important constraint is preserved: `offers-kmp/commonMain` has no core logic and no direct `shared-core` dependency.

## Prerequisites

- macOS with Xcode installed for iOS commands.
- Android SDK installed for Android commands.
- Gradle available as `gradle`, or set `GRADLE_CMD=./gradlew` if a Gradle wrapper is added later.
- Run all commands from this repo root.

Recommended shell setup:

```bash
export GRADLE_CMD="${GRADLE_CMD:-gradle}"
export GRADLE_USER_HOME="${GRADLE_USER_HOME:-/tmp/unloq-kmp-gradle-home}"
export IOS_DERIVED_DATA="${IOS_DERIVED_DATA:-/tmp/unloq-kmp-ios-derived-data}"
```

## Complete Build Sequence (Android & iOS)

Because of the hybrid architecture (where KMP bridges to native Swift/Android UI components), the iOS build requires manual pre-compilation steps, while Android is fully automated by Gradle.

### Build Android APK

The Android build is seamless because Gradle understands all the module dependencies natively.

```bash
# Build the Android APK (automatically resolves shared-core, native-android-wrapper, and offers-kmp)
./gradlew :kmp-merchant-app:assembleDebug
```
APK output: `kmp-merchant-app/build/outputs/apk/debug/kmp-merchant-app-debug.apk`

### Build iOS App

Before you build the iOS app, you must compile the shared core and package the Swift UI wrapper.

1. **Build the Shared Core XCFramework**
```bash
./gradlew :shared-core:assembleXCFramework
```

2. **Archive the Native Swift Wrapper**
The KMP build script is hardcoded to look for the compiled `.o` object files inside of Xcode Archives. You must build an archive for both Simulator and Device:
```bash
cd native-ios-wrapper
# Archive for iOS Simulator
xcodebuild archive -scheme NativeIosWrapperDemo -destination "generic/platform=iOS Simulator" -archivePath "archives/NativeIosWrapperDemo-iphonesimulator.xcarchive" SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES ENABLE_PREVIEWS=NO

# Archive for iOS Device
xcodebuild archive -scheme NativeIosWrapperDemo -destination "generic/platform=iOS" -archivePath "archives/NativeIosWrapperDemo-iphoneos.xcarchive" SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES ENABLE_PREVIEWS=NO
cd ..
```

3. **Build the iOS Merchant App**
You can now build the actual iOS app (Xcode will automatically trigger the Gradle script to compile the final `KmpMerchantShared.framework`):
```bash
xcodebuild build -project kmp-merchant-app/iosApp/KmpMerchantApp.xcodeproj -scheme KmpMerchantApp -sdk iphonesimulator -destination 'platform=iOS Simulator,name=iPhone 17'
```
*(Note: If you change the Swift code in `native-ios-wrapper` later, you MUST re-run Step 2 before building the app, otherwise KMP will use stale cached code.)*

## Build And Test Android

```bash
$GRADLE_CMD --no-daemon \
  :shared-core:jvmTest \
  :native-android-wrapper:testDebugUnitTest \
  :offers-kmp:assembleDebug \
  :kmp-merchant-app:assembleDebug
```

This proves the Android chain:

```text
kmp-merchant-app Android APK
  -> offers-kmp androidMain
  -> native-android-wrapper
  -> shared-core
```

## Run The Android Merchant App

Build:

```bash
$GRADLE_CMD --no-daemon :kmp-merchant-app:assembleDebug
```

APK output:

```text
kmp-merchant-app/build/outputs/apk/debug/kmp-merchant-app-debug.apk
```

Install on a connected device or emulator:

```bash
adb install -r kmp-merchant-app/build/outputs/apk/debug/kmp-merchant-app-debug.apk
```

Open `UNLOQ KMP Merchant` and tap `Show Offer Widget`. The Android path opens the SDK UI through `native-android-wrapper`.

## Run The Native iOS SDK Test

First build the core XCFramework consumed by the Swift SDK:

```bash
$GRADLE_CMD --no-daemon :shared-core:assembleUnloqOffersCoreReleaseXCFramework
```

Then run the Swift SDK XCTest on an iOS Simulator:

```bash
(
  cd native-ios-wrapper
  xcodebuild test \
    -scheme NativeIosWrapperDemo \
    -destination 'platform=iOS Simulator,name=iPhone 17,OS=26.1' \
    -derivedDataPath "$IOS_DERIVED_DATA"
)
```

If that simulator is not installed, list available destinations:

```bash
cd native-ios-wrapper
xcodebuild -scheme NativeIosWrapperDemo -showdestinations
cd -
```

Then replace the `-destination` value with an installed iOS Simulator.

This proves:

```text
native-ios-wrapper
  -> UnloqOffersCore.xcframework
  -> XCTest running on iOS Simulator
```

## Run The KMP Wrapper iOS Test

The KMP wrapper iOS simulator target depends on the Swift SDK framework generated by the previous `xcodebuild test` command. After running the native iOS SDK test, run:

```bash
$GRADLE_CMD --no-daemon \
  -PiosDerivedData="$IOS_DERIVED_DATA" \
  :offers-kmp:iosSimulatorArm64Test
```

Then validate the KMP merchant sample on iOS:

```bash
$GRADLE_CMD --no-daemon \
  -PiosDerivedData="$IOS_DERIVED_DATA" \
  :kmp-merchant-app:iosSimulatorArm64Test
```

This proves:

```text
offers-kmp iosSimulatorArm64Main
  -> Kotlin/Native cinterop
  -> native-ios-wrapper ObjC bridge
  -> shared-core
```

The same wiring is now used for both `iosSimulatorArm64` and `iosArm64` in `offers-kmp`.

## KMP Merchant App iOS Status

`kmp-merchant-app/iosApp` is a visual iOS merchant app. It imports only the `KmpMerchantShared` framework generated from `kmp-merchant-app`, which depends on `offers-kmp`. The Xcode build script first builds `native-ios-wrapper`, then Gradle builds the KMP framework. The iOS target of `offers-kmp` delegates to `native-ios-wrapper`, and that wrapper links the KMP core XCFramework.

Build it after generating the core XCFramework:

```bash
$GRADLE_CMD --no-daemon :shared-core:assembleUnloqOffersCoreReleaseXCFramework

xcodebuild build \
  -project kmp-merchant-app/iosApp/KmpMerchantApp.xcodeproj \
  -scheme KmpMerchantApp \
  -destination 'platform=iOS Simulator,name=iPhone 17,OS=26.1' \
  -derivedDataPath "$IOS_DERIVED_DATA"
```

Install and launch on a booted simulator:

```bash
xcrun simctl boot 'iPhone 17' || true
xcrun simctl install booted "$IOS_DERIVED_DATA/Build/Products/Debug-iphonesimulator/KmpMerchantApp.app"
xcrun simctl launch booted merchant.demo.kmp.ios
```

The app shows `UNLOQ KMP Merchant App`, imports `KmpMerchantShared`, calls the shared `KmpMerchantBridge`, and displays the generated offer summary + widget URL.

## Release Output Example

See `release-output-example/README.md`.

The example layout includes:

```text
release-output-example/core/1.0.0/
release-output-example/native-kotlin-wrapper/1.0.0/
release-output-example/native-swift-wrapper/1.0.0/
release-output-example/kmp-wrapper/1.0.0/
release-output-example/kmp-merchant-app/1.0.0/
```

It intentionally separates core assets, native wrapper assets, KMP wrapper assets, and merchant app assets.
