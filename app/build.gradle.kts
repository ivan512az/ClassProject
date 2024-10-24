//buildscript {
//    repositories {
//        google()  // Google's Maven repository
//        mavenCentral()
//    }
//    dependencies {
//        // Add this line
//        implementation("com.google.firebase:firebase-auth-ktx")
//    }
//}
//
//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.google.services)
//}
//
//android {
//    namespace = "com.example.classproject"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.classproject"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
//}
//
//dependencies {
//
//    implementation (platform("com.google.firebase:firebase-bom:32.0.0"))
//    implementation ("com.google.firebase:firebase-auth")
//    implementation("androidx.core:core-ktx:1.7.0")
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//}


//Second version

buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")  // Example of a classpath dependency
        classpath("com.google.gms:google-services:4.3.14")  // Firebase plugin dependency for Google services
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.classproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.classproject"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))  // Firebase BOM for version alignment
    implementation("com.google.firebase:firebase-auth")  // Firebase Auth dependency
    implementation("com.google.firebase:firebase-auth-ktx")  // Firebase KTX extension for Auth
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.android.gms:play-services-auth:20.6.0")  // For Google login



    implementation("androidx.navigation:navigation-compose:2.5.3")  // Or the latest version
    implementation("androidx.core:core-ktx:1.7.0")
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
