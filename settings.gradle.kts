pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "unloq-kmp-library-demo"

include(":shared-core")
include(":native-android-wrapper")
include(":offers-kmp")
include(":kmp-merchant-app")
