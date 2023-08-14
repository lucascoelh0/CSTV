pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "GO Matches"
include(":app")
include(":domain")
include(":data")
include(":core")
