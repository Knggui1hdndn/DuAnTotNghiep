plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")

    id ("androidx.navigation.safeargs")

}

android {
    namespace = "com.knd.duantotnghiep.duantotnghiep"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.knd.duantotnghiep.duantotnghiep"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    //Navigation
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    //google
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    //hilt-dagger
    implementation ("com.google.dagger:hilt-android:2.44")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.44")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.picasso:picasso:2.71828")


}
