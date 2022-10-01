plugins {
    id("jvm-conventions")
    `maven-publish`
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("shadow") {
                artifact(this@tasks["shadowJar"]) {
                    classifier = null
                }

                artifactId = "${rootProject.name}-${project.name}"

                repositories {
                    maven {
                        credentials {
                            username = System.getenv("REPO_USERNAME")
                            password = System.getenv("REPO_PASSWORD")
                        }

                        url = uri("https://repo.craftyourtown.com/repository/maven-public/")
                    }
                }
            }
        }
    }
}