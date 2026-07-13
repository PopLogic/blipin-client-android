import com.google.devtools.ksp.gradle.KspExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("keystores/key.properties")
if (keystorePropertiesFile.exists()) {
    FileInputStream(keystorePropertiesFile).use { keystoreProperties.load(it) }
}

android {
    namespace = "com.poplogic.blipin"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.poplogic.blipin"
        minSdk = 30
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val versionPropertiesFile = file("version.properties")
        if (versionPropertiesFile.exists()) {
            val versionProperties =
                Properties().apply {
                    load(FileInputStream(versionPropertiesFile))
                }

            val majorVersion = (versionProperties.getProperty("versionMajor") ?: "0").toInt()
            val minorVersion = (versionProperties.getProperty("versionMinor") ?: "0").toInt()
            val patchVersion = (versionProperties.getProperty("versionPatch") ?: "0").toInt()
            val build = (System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 1).coerceAtMost(999)
            versionName = "$majorVersion.$minorVersion.$patchVersion"
            versionCode =
                majorVersion * 10_000_000 + minorVersion * 100_000 + patchVersion * 1_000 + build
        }
    }

    signingConfigs {
//        getByName("debug") {
//            keyAlias = "NonRelease"
//            keyPassword = "NonRelease"
//            storeFile = keystoreProperties["storeFile"]?.let { file(it) }
//            storePassword = "NonRelease"
//        }

        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = keystoreProperties.getProperty("storeFile")?.let { file(it) }
            storePassword = keystoreProperties.getProperty("storePassword")
        }

        create("validation") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = keystoreProperties.getProperty("storeFile")?.let { file(it) }
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }

        create("validation") {
            initWith(getByName("release"))
            matchingFallbacks.add("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "validation-rules.pro",
            )
            signingConfig = signingConfigs.getByName("validation")
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    flavorDimensions += listOf<String>("environment")
    productFlavors {
        create("development") {
            applicationIdSuffix = ".development"
            versionNameSuffix = "-dev"
            dimension = "environment"
        }

        create("staging") {
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            dimension = "environment"
        }

        create("production") {
            dimension = "environment"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
// secrets {
//    // Optionally specify a different file name containing your secrets.
//    // The plugin defaults to "local.properties"
//    propertiesFileName = "secrets.properties"
//
//    // A properties file containing default secret values. This file can be
//    // checked in version control.
//    defaultPropertiesFileName = "local.properties"
//
//    // Configure which keys should be ignored by the plugin by providing regular expressions.
//    // "sdk.dir" is ignored by default.
//    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
//    ignoreList.add("sdk.*") // Ignore all keys matching the regexp "sdk.*"
// }

extensions.configure<KspExtension> {
    arg("KOIN_CONFIG_CHECK", "false") // 關閉 Koin 註解編譯期檢查
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
    implementation(libs.lottie.compose)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity)
    implementation(libs.compose.material.icons)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
    implementation(libs.play.services.location)
    add("ksp", libs.koin.ksp.compiler)
    implementation(libs.insert.koin.koin.compose.viewmodel)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose.viewmodel.navigation)

    // firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // credentials manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Google Map
    implementation(libs.secrets.gradle.plugin)
    implementation(libs.play.services.maps.v1820)
    implementation(libs.maps.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
