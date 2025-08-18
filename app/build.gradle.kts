plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.goongoonaloAssignment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.goongoonaloAssignment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "THE_MOVIE_DB_API_TOKEN", "API_KEY" )
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.appModuleLibraries)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.roomksp)
    ksp(libs.hilt.ext.compiler)
    ksp(libs.hilt.work)
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":customViews"))
    implementation(project(":core:network"))
}