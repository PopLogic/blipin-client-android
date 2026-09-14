plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "3.5.2"
}

android {
    namespace = "com.poplogic.blipin.api.auth"
    compileSdk = 36

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    flavorDimensions += listOf<String>("environment")
    productFlavors {
        create("development") {
            dimension = "environment"
            buildConfigField("String", "FLAVOR", "\"development\"")
            buildConfigField("String", "ENVIRONMENT", "\"development\"")
            buildConfigField("boolean", "DEBUG_LOGS", "true")
        }

        create("staging") {
            dimension = "environment"
            buildConfigField("String", "FLAVOR", "\"staging\"")
            buildConfigField("String", "ENVIRONMENT", "\"staging\"")
            buildConfigField("boolean", "DEBUG_LOGS", "true")
        }

        create("production") {
            dimension = "environment"
            buildConfigField("String", "FLAVOR", "\"production\"")
            buildConfigField("String", "ENVIRONMENT", "\"production\"")
            buildConfigField("boolean", "DEBUG_LOGS", "false")
        }
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDirs("src/generated-ktor-client/src/main/kotlin")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":common:base"))
    implementation(project(":api:common"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    // ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    // openapi
    implementation(libs.moshi.kotlin)

    // serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.datetime)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
