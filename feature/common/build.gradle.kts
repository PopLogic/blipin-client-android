plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("io.ktor.plugin") version "3.5.2"
    id("kotlin-parcelize")
}

android {
    namespace = "com.poplogic.blipin.feature.common"
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

    buildFeatures {
        compose = true
        buildConfig = true
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

}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(projects.common.base)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.lottie.compose)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity)
    implementation(libs.compose.material.icons)
    implementation(libs.kotlinx.datetime)

    // credentials manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
