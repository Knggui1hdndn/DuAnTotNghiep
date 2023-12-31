
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        var nav_version = "2.5.3"
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
plugins {
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.application") version "8.1.2" apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false
}