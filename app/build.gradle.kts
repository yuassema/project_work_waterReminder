plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project_work"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.project_work"
        minSdk = 29
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("androidx.core:core:1.16.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation ("androidx.navigation:navigation-fragment:2.8.9")
    implementation ("androidx.navigation:navigation-ui:2.8.9")
    implementation ("androidx.room:room-runtime:2.7.0")
    annotationProcessor ("androidx.room:room-compiler:2.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}