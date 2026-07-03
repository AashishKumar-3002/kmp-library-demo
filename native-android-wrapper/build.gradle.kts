plugins {
    kotlin("jvm")
}

dependencies {
    // In this demo, the wrapper depends on the local project.
    // In a real separated Android SDK repo, this would be:
    // implementation("com.useunloq:unloq-offers-core:1.0.0")
    implementation(project(":shared-core"))

    testImplementation(kotlin("test"))
}
