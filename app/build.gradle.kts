plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mapssgps"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mapssgps"
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
}

dependencies {

    implementation(libs.appcompat)

    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.support.annotations)
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.libraries.places:places:3.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}