plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.useunloq.offers.android"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // In this demo, the wrapper depends on the local project.
    // In a real separated Android SDK repo, this would be:
    // implementation("com.useunloq:unloq-offers-core:1.0.0")
    implementation(project(":shared-core"))
    implementation("androidx.fragment:fragment-ktx:1.8.2")
    implementation("com.google.android.material:material:1.12.0")

    testImplementation(kotlin("test"))
}
