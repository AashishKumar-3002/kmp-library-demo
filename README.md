# KMP Library Demo

This repo is now a small runnable PoC for the architecture claim, not just a diagram.

The claim being validated is:

```text
KMP merchants can consume a merchant-facing KMP SDK from commonMain
That KMP SDK can own the UI entrypoint
The UI shell is platform-native through expect/actual
Existing Android and Swift wrappers can still remain secondary consumers of the shared core
```

## Layout

```text
kmp-library-demo/
  shared-core/
    build.gradle.kts
    src/commonMain/kotlin/...
    src/androidMain/kotlin/...
    src/iosMain/kotlin/...

  offers-kmp/
    build.gradle.kts
    src/commonMain/kotlin/...
    src/androidMain/kotlin/...
    src/iosMain/kotlin/...

  android-demo-app/
    build.gradle.kts
    src/main/java/...
    src/main/res/...
```

## What This PoC Actually Proves

### 1. `shared-core` is the headless KMP core

It exposes the shared business logic:

- `initialize(...)`
- `setUser(...)`
- `setAttribution(...)`
- `emitEvent(...)`
- `evaluate(...)`

The returned `OfferDecision` includes:

- eligibility
- reward copy
- platform marker
- widget URL assembled from shared state
- debug summary proving shared state was used

### 2. `offers-kmp` is the merchant-facing KMP SDK

This is the actual merchant integration path in the Phase 1 shape.

It owns:

- the common `UnloqOffers` API
- the `showWidget(...)` entrypoint
- `expect/actual` widget presentation
- Android rendering helper that opens a real bottom sheet + WebView

`shared-core` decides eligibility and builds the widget URL. `offers-kmp` presents that URL through a native shell:

- Android actual: `bottom sheet + WebView`
- iOS actual: `sheet + WKWebView`
- JVM/macOS actuals: non-UI placeholders used only so local builds still work

### 3. `android-demo-app` is the thing to open

This is the sample merchant app you can actually run to see UI.

The button in `MainActivity` initializes `offers-kmp`, evaluates the offer, and opens a bottom sheet with a WebView-backed demo widget.

### 4. What was removed from the main flow

- `poc-cli` is no longer part of the build flow
- `native-android-wrapper`, `native-ios-wrapper`, and `kmp-merchant-app` are no longer included in Gradle settings
- the repo now focuses on `shared-core` + `offers-kmp` + `android-demo-app`

## See UI

```text
GRADLE_USER_HOME=/private/tmp/codex-gradle-home ../unloq-offer-sdk-kotlin/gradlew --no-daemon -p "$PWD" :android-demo-app:assembleDebug
```

That produces the sample APK at:

```text
android-demo-app/build/outputs/apk/debug/android-demo-app-debug.apk
```

Install it on an emulator/device and tap `Show Offer Widget`.

## KMP Build Commands

```text
GRADLE_USER_HOME=/private/tmp/codex-gradle-home ../unloq-offer-sdk-kotlin/gradlew --no-daemon -p "$PWD" :shared-core:jvmTest :offers-kmp:jvmTest
GRADLE_USER_HOME=/private/tmp/codex-gradle-home ../unloq-offer-sdk-kotlin/gradlew --no-daemon -p "$PWD" :shared-core:assembleUnloqOffersCoreReleaseXCFramework
GRADLE_USER_HOME=/private/tmp/codex-gradle-home ../unloq-offer-sdk-kotlin/gradlew --no-daemon -p "$PWD" :offers-kmp:assemble
```

Those produce:

```text
shared-core/build/XCFrameworks/release/UnloqOffersCore.xcframework
shared-core/build/libs/shared-core-jvm-1.0.0.jar
offers-kmp/build/libs/offers-kmp-jvm.jar
offers-kmp/build/outputs/aar/offers-kmp-release.aar
offers-kmp/build/bin/androidReleaseLibrary/offers-kmp-release.aar
```
