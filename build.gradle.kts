import com.android.build.gradle.internal.tasks.factory.dependsOn

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("org.sonarqube") version "3.5.0.2730"
}

sonar {
    properties {
        property("sonar.projectKey", "estivensh4_maas")
        property("sonar.organization", "estivensh4-1")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.token", "fdde4783e7ee9b93d384271c5a75b8be6670c5fc")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "$buildDir/reports/kover/report.xml"
        )
    }
}

tasks.sonar.dependsOn(":koverXmlReport")