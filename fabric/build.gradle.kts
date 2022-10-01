plugins {
    id("jvm-conventions")
    id("fabric-loom") version "1.0-SNAPSHOT"
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.adventure.fabric)
    include(libs.adventure.fabric)
    modImplementation(libs.cloud.fabric)
    include(libs.cloud.fabric)
}
