plugins {
    id("jvm-conventions")
}

dependencies {
    compileOnlyApi(libs.stdlib)
    compileOnlyApi(libs.coroutines)
    compileOnlyApi(libs.adventure)
}
