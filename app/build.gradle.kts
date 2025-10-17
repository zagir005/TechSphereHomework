plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.zagirlek.nytimes"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.zagirlek.nytimes"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(name = "WEATHER_BASE_URL", value = "\"https://api.weatherapi.com/v1/\"", type = "String")
        buildConfigField(name = "WEATHER_API_KEY", value = "\"dd9eadf389194d2c828170710251809\"", type = "String")

        buildConfigField(name = "NEWS_BASE_URL", value = "\"https://newsdata.io/api/1/\"", type = "String")
        buildConfigField(name = "NEWS_API_KEY", value = "\"pub_1bcf9d91fc5240eda6af74c00b7e628d\"", type = "String")
        buildConfigField(name = "NEWS_API_KEY_1", value = "\"pub_e7fb55d0abb84d219bd323216254f33c\"", type = "String")
        buildConfigField(name = "NEWS_API_KEY_2", value = "\"pub_412b85944c0540c6b7e4f65e4fc2af4e\"", type = "String")
        buildConfigField(name = "AVAILABLE_DOMAINS", value = "\"nytimes,bbc,forbes,theguardian,washingtonpost\"", type = "String")

        buildConfigField(name = "EXTRACTOR_BASE_URL", value = "\"https://api.articlextractor.com/v1/\"", type = "String")
        buildConfigField(name = "EXTRACTOR_API_KEY", value = "\"BBm76KtyDK4rCVo1M4v4f2jh0eZ2LUV1SHuBrmy4\"", type = "String")
        buildConfigField(name = "EXTRACTOR_API_KEY_1", value = "\"8F15YKkE2jOzXwk0YxYPYYxrYp9CqaH56r2yuR41\"", type = "String")
        buildConfigField(name = "EXTRACTOR_API_KEY_2", value = "\"hHTr6AVMaruVdfk7iNmpZKmKqoHBj8ZnpvJvJhXm\"", type = "String")
        buildConfigField(name = "EXTRACTOR_API_KEY_3", value = "\"667018glHoOpF6HxrzbKJP6x273uhGn7SrFYAbKH\"", type = "String")
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
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.android)
    implementation(projects.core.remote)
    implementation(projects.core.local)

    implementation(projects.data.auth)
    implementation(projects.data.weather)
    implementation(projects.data.news)

    implementation(projects.domain.auth)
    implementation(projects.domain.weather)
    implementation(projects.domain.news)

    implementation(projects.feature.auth)
    implementation(projects.feature.splash)
    implementation(projects.feature.main)
    implementation(projects.feature.weather)
    implementation(projects.feature.news.articledetails)
    implementation(projects.feature.news.latest)
    implementation(projects.feature.news.favorite)

    implementation(libs.decompose)
    implementation(libs.decompose.extension)
    implementation(libs.essenty.coroutines)
    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.coroutines)

    ksp(libs.androidx.room.ksp)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    implementation(libs.paging.common)
    implementation(libs.paging.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)

    implementation(libs.shimmer)

    implementation(libs.material.icons.extended)
    implementation(libs.androidx.ui.animation)
    implementation(libs.androidx.core.splash)
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
    testImplementation(kotlin("test"))
}