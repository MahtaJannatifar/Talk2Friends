plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.talk2friends"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.talk2friends"
        minSdk = 24
        targetSdk = 33
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
    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0-alpha03")

// Gmail API
    implementation("com.google.apis:google-api-services-oauth2:v2-rev157-1.25.0")
// Google Account Authentication
    implementation("com.google.android.gms:play-services-auth:20.7.0")

// Google API Client for Java
    implementation("com.google.api-client:google-api-client:1.31.5")
    implementation("com.google.api-client:google-api-client-android:1.33.0")
    implementation("com.google.api-client:google-api-client-gson:1.31.5")

// Gson
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.google.firebase:firebase-auth:22.2.0")

    implementation("com.google.apis:google-api-services-gmail:v1-rev29-1.20.0")
    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation ("com.sun.mail:android-activation:1.6.5")
    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation("com.sun.mail:android-activation:1.6.5")

}
