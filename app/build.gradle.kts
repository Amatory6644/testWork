plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt") version "2.3.10"
   // id("com.google.devtools.ksp") version "2.2.21-2.0.5"
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("androidx.room") version "2.8.4" apply false


}

android {
    namespace = "com.example.testwork"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.testwork"
        minSdk = 33
        targetSdk = 36
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
    kotlin.compilerOptions{
        jvmTarget
    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        compose = true
    }
    packaging{
        //resources.pickFirsts
//        resources.excludes.add("com.google.dagger:dagger-compiler:2.57.1/dagger-compiler-2.57.1.jar")
        // it helped delete
    }
}

dependencies {

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.material)
    implementation(libs.hilt.android)
//implementation(libs.hilt.android.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}