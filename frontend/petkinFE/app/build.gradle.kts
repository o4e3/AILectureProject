import java.util.Properties


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt) // Hilt 플러그인 추가
    id("kotlin-kapt") // Kapt 플러그인 추가
    id("com.google.devtools.ksp")
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.rtl.petkinfe"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.rtl.petkinfe"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        defaultConfig {
            val apiBaseUrl: String = getApiBaseUrl()
            buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
        }

        buildConfigField(
            "String",
            "KAKAO_APP_KEY",
            "\"${properties["KAKAO_APP_KEY"] ?: ""}\""
        )
        manifestPlaceholders["KAKAO_APP_KEY"] = properties["KAKAO_APP_KEY"].toString()
        resValue(
            "string",
            "KAKAO_REDIRECT_URI",
            properties["KAKAO_REDIRECT_URI"].toString()
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.kakao.sdk.user)
    implementation(libs.hilt.android) // Hilt Android 라이브러리
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt(libs.hilt.compiler) // Hilt 컴파일러
    implementation(libs.androidx.datastore.preferences)
    implementation("com.google.accompanist:accompanist-pager:0.30.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
}

// local.properties 파일에서 API_BASE_URL 읽어오는 함수
fun getApiBaseUrl(): String {
    val localProperties = project.rootProject.file("local.properties")
    if (localProperties.exists()) {
        val properties = Properties().apply {
            load(localProperties.inputStream())
        }
        return properties.getProperty("API_BASE_URL", "https://example.com/")
    }
    return "https://example.com/" // 기본값
}