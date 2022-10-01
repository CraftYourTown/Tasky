rootProject.name = "tasky"

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net")
    }
}

include(
    "api",
    "common",
    "fabric",
    "spigot",
    "sponge"
)