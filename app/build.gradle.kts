plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services) // Kotlin Annotation Processing Tool
}

android {
    namespace = "com.harshm.spotifyclone"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.harshm.spotifyclone"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {

        }
        release {

        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

        // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    // LiveData KTX (for observing LiveData with coroutines)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    // Lifecycle Runtime KTX (for lifecycle-aware coroutines)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    // SavedStateHandle KTX (for ViewModel state persistence)
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.7")

    // Compose Lifecycle Runtime KTX (for Jetpack Compose lifecycle integration)
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Lifecycle Service (For lifecycle-aware services)
    implementation("androidx.lifecycle:lifecycle-service:2.8.7")

    // Lifecycle Process (For process lifecycle management)
    implementation("androidx.lifecycle:lifecycle-process:2.8.7")

    // Lifecycle Common (For annotations like DefaultLifecycleObserver)
    implementation("androidx.lifecycle:lifecycle-common-java8:2.8.7")
    // Kotlin Coroutines Core (Standard Library for Coroutines)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // Kotlin Coroutines for Android (Main Dispatcher Support)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Navigation Component (KTX for Kotlin Coroutines)
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.9")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.9")

    // Activity KTX (for ViewModel delegation using by viewModels())
    implementation("androidx.activity:activity-ktx:1.10.1")
    // Dagger Hilt Core
    implementation("com.google.dagger:hilt-android:2.51.1")

    // Hilt Compiler (Annotation Processor)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    // Hilt for ViewModel (ViewModel Scoped Injection)
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    // Timber logging library
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Exo player
    implementation("androidx.media3:media3-exoplayer:1.6.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.6.0")
    implementation("androidx.media3:media3-ui:1.6.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-firestore")




}