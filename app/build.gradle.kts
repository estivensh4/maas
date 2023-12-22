import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.estivensh4.maasapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.estivensh4.maasapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }
        buildConfigField("String", "API_TOKEN", prop.getProperty("API_TOKEN"))
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.material3)
    implementation(libs.ktor.core)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.json)
    implementation(libs.ktor.logging)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.junit)
    testImplementation(libs.junitExt)
    testImplementation(libs.truth)
    testImplementation(libs.mock)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.ktor.test)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.mock)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation("org.slf4j:slf4j-simple:2.0.7")
}

koverReport {

    // filters for all report types of all build variants
    filters {
        excludes {
            classes(
                "*Fragment",
                "*Fragment\$*",
                "*Activity",
                "*Activity\$*",
                "*.databinding.*",
                "*.BuildConfig"
            )
        }
    }

    androidReports("debug") {
        // filters for all report types only of 'release' build type
        filters {
            excludes {
                classes(
                    "*Fragment",
                    "*Fragment\$*",
                    "*Activity",
                    "*Activity\$*",
                    "*.databinding.*",
                    "*.BuildConfig",

                    // excludes debug classes
                    "*.DebugUtil"
                )
            }
        }
    }
}