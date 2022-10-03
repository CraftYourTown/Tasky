plugins {
    id("jvm-conventions")
}

dependencies {
    implementation(project(":common"))

    compileOnly(libs.spigot)
    implementation(libs.adventure.bukkit)
    implementation(libs.cloud.paper)
}
