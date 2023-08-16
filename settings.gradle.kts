pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "CSTV"
include(":app")
include(":domain")
include(":data")
include(":core")
