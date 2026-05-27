import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()
    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            // Podstawowe biblioteki KMP
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.jetbrains.lifecycle.runtimeCompose)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)

            // Ktor (Wieloplatformowy klient HTTP)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // COIL 3 (Ładowanie obrazków z sieci za pomocą Ktora)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp) // Ktor dla Androida używa OkHttp
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp) // Ktor dla Windowsa używa OkHttp
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js) // Ktor dla przeglądarki używa silnika JS
        }
    }
}

android {
    namespace = "pl.lipov.technologieinternetowe"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "pl.lipov.technologieinternetowe"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "pl.lipov.technologieinternetowe.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb)
            packageName = "pl.lipov.technologieinternetowe"
            packageVersion = "1.0.0"
        }
    }
}