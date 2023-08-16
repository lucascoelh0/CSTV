plugins {
    id(Plugin.Android.library)
    id(Plugin.Jetbrains.kotlin)
    kotlin(Plugin.Kotlin.kapt)
}

apply {
    from("$rootDir/ktlint.gradle")
}

android {
    namespace = Modules.Domain.namespace
    compileSdk = Modules.Domain.compileSdk

    defaultConfig {
        minSdk = Modules.Domain.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(project(Modules.Core.name))
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Libraries.moshi)
    kapt(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltPlugin)
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.coroutinesTesting)
}