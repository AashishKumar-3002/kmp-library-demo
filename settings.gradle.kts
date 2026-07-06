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
    versionCatalogs {
        create("libs") {
            from(files("kmp-merchant-compose-app/gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "unloq-kmp-library-demo"

include(":shared-core")
include(":native-android-wrapper")
include(":offers-kmp")
include(":kmp-merchant-app")
include(":kmp-merchant-compose-app:shared")
include(":kmp-merchant-compose-app:androidApp")
