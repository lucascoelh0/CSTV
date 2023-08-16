plugins {
    id(Plugin.Android.application)
    id(Plugin.Kotlin.android)
    id(Plugin.Jetbrains.kotlin)
    id(Plugin.Hilt.android)
    id(Plugin.Ksp.android)
    kotlin(Plugin.Kotlin.kapt)
}

android {
    namespace = Modules.App.namespace
    compileSdk = Modules.App.compileSdk

    defaultConfig {
        applicationId = "com.luminay.gomatches"
        minSdk = Modules.App.minSdk
        targetSdk = Modules.App.targetSdk
        versionCode = Modules.App.versionCode
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Modules.Data.name))
    implementation(project(Modules.Core.name))
    implementation(project(Modules.Domain.name))
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)
    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.coilCompose)
    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Compose.lifecycleRuntime)
    implementation(Dependencies.Libraries.composeShimmer)
    implementation(Dependencies.Hilt.hiltPlugin)
    implementation(Dependencies.Hilt.hiltNavigationCompose)
    implementation(Dependencies.SquareUp.okhttp)
    implementation(Dependencies.SquareUp.okhttpLoggingInterceptor)
    implementation(Dependencies.SquareUp.retrofit)
    implementation(Dependencies.SquareUp.retrofitConverterMoshi)
    implementation(Dependencies.Libraries.networkResponseAdapter)
    implementation(Dependencies.Libraries.composeDestinationsCore)
    implementation(Dependencies.AndroidX.splashScreen)
    ksp(Dependencies.Libraries.composeDestinationsKsp)
    kapt(Dependencies.Hilt.hiltCompiler)
    kapt(Dependencies.Libraries.moshiKapt)
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.coroutinesTesting)
    androidTestImplementation(Dependencies.Test.androidxTestExtJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)
    androidTestImplementation(Dependencies.Test.uiTestJunit4)
    debugImplementation(Dependencies.Compose.uiTooling)
}
