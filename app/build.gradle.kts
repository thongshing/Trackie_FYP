plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.trackie_fyp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.trackie_fyp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "kotlin/reflect/reflect.kotlin_builtins"
            excludes += "kotlin/coroutines/coroutines.kotlin_builtins"
            excludes += "kotlin/collections/collections.kotlin_builtins"
            excludes += "kotlin/internal/internal.kotlin_builtins"
            excludes += "kotlin/kotlin.kotlin_builtins"
            excludes +=  "kotlin/ranges/ranges.kotlin_builtins"
            excludes += "kotlin/annotation/annotation.kotlin_builtins"
        }
    }
}

configurations.all {
    exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    exclude(group = "com.google.auto.value", module = "auto-value")
    //exclude(group = "org.jetbrains", module = "annotations")
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.sqlite:sqlite:2.4.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("androidx.room:room-compiler-processing-testing:2.6.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.mlkit:text-recognition:16.0.0")
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")
    implementation("com.google.guava:guava:31.0.1-android")
    implementation("com.google.accompanist:accompanist-permissions:0.16.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
    implementation("com.google.dagger:hilt-android:2.49")
//    implementation ("com.github.tehras:charts:0.2.4-alpha")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    kapt("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("com.android.billingclient:billing:4.0.0") {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
        exclude(group = "com.google.auto.value", module = "auto-value")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}



