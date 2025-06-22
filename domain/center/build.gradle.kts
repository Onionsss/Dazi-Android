@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.dazi.android.hilt)
    alias(libs.plugins.dazi.android.compose)
    alias(libs.plugins.dazi.android.ksj)
    alias(libs.plugins.dazi.android.network)
    alias(libs.plugins.dazi.android.coil)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.onion.center"
    compileSdk = libs.versions.androidMaxSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    composeOptions {
        //kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(":user:login-protocol"))
    implementation(project(":user:login"))
    implementation(project(":router"))
    implementation(project(":resource"))
    implementation(project(":domain:center-protocol"))
    implementation(project(":core:common"))
    implementation(project(":core:platform-hub"))
    implementation(project(":feature:home"))
    implementation(project(":feature:my"))

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    //okhttp
    implementation(libs.okhttp.core)
    implementation(libs.retrofit.core)

    implementation("io.github.h07000223:flycoTabLayout:3.0.0")
    implementation("com.canopas.compose-animated-navigationbar:bottombar:1.0.1")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("com.airbnb.android:lottie-compose:6.0.0")
    //komposable
    implementation(libs.komposable)
}