plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("poc.demo.MainKt")
}

dependencies {
    implementation(project(":kmp-merchant-app"))
    implementation(project(":offers-kmp"))
    implementation(project(":native-android-wrapper"))
    implementation(project(":shared-core"))
}
