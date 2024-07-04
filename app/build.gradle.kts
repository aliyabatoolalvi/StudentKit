plugins {
    alias(libs.plugins.androidApplication)
//    id("com.android.application")
    id("com.google.gms.google-services")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
    alias(libs.plugins.googleFirebaseCrashlytics)
}

android {
    namespace = "com.aliya.studentkit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aliya.studentkit"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    //room sql database for persistence
    implementation(libs.play.services.maps)
    implementation(libs.firebase.crashlytics)
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")


    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okio:okio:3.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    // picasso
    implementation("com.squareup.picasso:picasso:2.8")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-inappmessaging-display")

    implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}