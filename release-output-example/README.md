# Release Output Example

This folder shows the expected release artifact layout for the final architecture.

Version used in this example:

```text
1.0.0
```

## Artifact Ownership

```text
core/
  Produced by the KMP core repo.
  Consumed by native-kotlin-wrapper and native-swift-wrapper.

native-kotlin-wrapper/
  Produced by the native Android/Kotlin SDK repo.
  Depends on core Maven artifacts.
  Consumed by Android merchants, React Native Android, Flutter Android, and offers-kmp androidMain.

native-swift-wrapper/
  Produced by the native Swift/iOS SDK repo.
  Depends on core UnloqOffersCore.xcframework.zip.
  Consumed by iOS merchants, React Native iOS, Flutter iOS, and offers-kmp iosMain through ObjC/cinterop.

kmp-wrapper/
  Produced by the KMP UI wrapper repo.
  commonMain is only a facade.
  androidMain delegates to native-kotlin-wrapper.
  iosMain delegates to native-swift-wrapper.

kmp-merchant-app/
  Produced by the KMP merchant sample app.
  Android output is an installable APK.
  iOS output is an installable simulator app from kmp-merchant-app/iosApp.
```

## Expected Tree

```text
release-output-example/
  core/1.0.0/
    ios/
      UnloqOffersCore.xcframework.zip
      UnloqOffersCore.xcframework.zip.checksum
      release-manifest.json
    maven/com/useunloq/
      unloq-offers-core/1.0.0/
      unloq-offers-core-android/1.0.0/
      unloq-offers-core-iosarm64/1.0.0/
      unloq-offers-core-iossimulatorarm64/1.0.0/

  native-kotlin-wrapper/1.0.0/
    maven/com/useunloq/unloq-offers-android/1.0.0/

  native-swift-wrapper/1.0.0/
    ios/
      UnloqOffers.xcframework.zip
      UnloqOffers.xcframework.zip.checksum
      release-manifest.json

  kmp-wrapper/1.0.0/
    maven/com/useunloq/
      unloq-offers-kmp/1.0.0/
      unloq-offers-kmp-android/1.0.0/
      unloq-offers-kmp-iosarm64/1.0.0/
      unloq-offers-kmp-iossimulatorarm64/1.0.0/

  kmp-merchant-app/1.0.0/
    android/
      kmp-merchant-app-debug.apk
      output-metadata.json
    ios/
      KmpMerchantApp.app.zip
      KmpMerchantApp.xcresult
```

These are example names. Real releases should also include Gradle/Maven checksums, module metadata, Swift package checksums, provenance, and CI build metadata.
