plugins {
    id("jvm-conventions")
    id("org.spongepowered.gradle.plugin") version "2.0.2"
}

dependencies {
    implementation(project(":common"))

    compileOnly(libs.sponge)
    implementation(libs.adventure.sponge)
    implementation(libs.cloud.sponge)
}
