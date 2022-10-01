plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
}