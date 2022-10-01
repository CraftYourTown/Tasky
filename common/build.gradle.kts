plugins {
    id("jvm-conventions")
}

dependencies {
    api(project(":api"))
    api(libs.stdlib)
    api(libs.configurate)
    api(libs.adventure)
    api(libs.cloud.core)
    api(libs.cloud.annotations)
    api(libs.cloud.extras) {
        isTransitive = false // We depend on adventure separately
    }
}