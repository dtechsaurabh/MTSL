plugins {
    alias(libs.plugins.kotlin.compose)
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt") // Ensure this is present

}

//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.kotlin.android)
//    //alias(libs.plugins.kotlin.kapt)
//    id("kotlin-kapt")
//
//}

android {
    namespace = "com.example.mtsl"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mtsl"
        minSdk = 21
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
        compose = true
        viewBinding = true

    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.appcompat)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Retrofit for API Calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Coroutines for Async Tasks
    implementation(libs.coroutines)

    // Glide for Image Loading
    implementation(libs.glide)
     kapt(libs.glide.compiler)
    kapt(libs.glide.compiler)

    // RecyclerView for displaying lists
    implementation(libs.recyclerview)

    // Material Components for UI
    implementation(libs.material)

    // Unit Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)


    implementation(libs.coroutines.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.coroutines.adapter)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.retrofit.rxjava)
    implementation(libs.javax.inject) // For @Inject annotation
    implementation(libs.androidx.cardview)


    // Unit Testing
//    testImplementation(libs.mockito.core)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.room.testing)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)

    // UI Testing (Espresso)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)

    testImplementation(libs.arch.core.testing)
    testImplementation(kotlin("test"))
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")  //
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("io.mockk:mockk:1.13.4")


}
kapt {
    correctErrorTypes = true
}
