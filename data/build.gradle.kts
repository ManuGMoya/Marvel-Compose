import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
}

android {
    namespace = "com.manudev.data"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties: Properties =
            Properties().apply {
                load(FileInputStream(File(rootProject.rootDir, "local.properties")))
            }
        buildConfigField(
            type = "String",
            name = "MARVEL_PRIVATE_KEY",
            value = localProperties.getProperty("MARVEL_PRIVATE_KEY") ?: "",
        )
        buildConfigField(
            type = "String",
            name = "MARVEL_PUBLIC_KEY",
            value = localProperties.getProperty("MARVEL_PUBLIC_KEY") ?: "",
        )
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)

    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.loggingInterceptor)

    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
}