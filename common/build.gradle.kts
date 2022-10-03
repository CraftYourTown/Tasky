plugins {
    id("jvm-conventions")
}

dependencies {
    api(project(":api"))
    api(libs.stdlib)
    api(libs.coroutines)
    api(libs.scripting.common)
    api(libs.scripting.jvm)
    api(libs.scripting.jvm.host)
    api(libs.scripting.dependencies)
    api(libs.scripting.maven)
    api(libs.configurate)
    api(libs.ebean)
    api(libs.adventure)
    api(libs.adventure.minimessage)
    api(libs.cloud.core)
    api(libs.cloud.annotations)
    api(libs.cloud.extras) {
        isTransitive = false // We depend on adventure separately
    }
}
