plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")

    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

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
     configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }
}
dependencies {
    implementation ("io.socket:socket.io-client:2.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation( "com.google.android.material:material:1.11.0-beta01")
    implementation ("org.modelmapper:modelmapper:2.4.4")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    //Navigationimplementation ‘com.facebook.shimmer:shimmer:0.5.0’
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    //google
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
     //hilt-dagger
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.libraries.places:places:3.2.0")
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    annotationProcessor("com.google.dagger:hilt-compiler:2.44")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
     implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
}
