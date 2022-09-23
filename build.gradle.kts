fun properties(key: String) = project.findProperty(key).toString()

fun versionFromTag(): String  {
    val version = System.getenv("TAG_NAME") ?: properties("project-version")
    return version.replace("v", "")
}

plugins {
    kotlin("jvm") version "1.7.10"
    jacoco
    `maven-publish`
}

group = properties("project-group")
version = properties("project-version")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/ng-galien/ddd-kotlin")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        publications {
            create<MavenPublication>("github") {
                groupId = properties("project-group")
                artifactId = properties("project-artifact")
                version = versionFromTag()
                from(components["java"])
                pom {
                    name.set(properties("project-name"))
                    description.set(properties("project-description"))
                    url.set(properties("project-url"))
                    licenses {
                        license {
                            name.set(properties("project-license-name"))
                            url.set(properties("project-license-url"))
                        }
                    }
                    developers {
                        developer {
                            id.set(properties("project-developer-id"))
                            name.set(properties("project-developer-name"))
                            email.set(properties("project-developer-email"))
                        }
                    }
                    scm {
                        url.set(properties("project-scm-url"))
                        connection.set(properties("project-scm-connection"))
                        developerConnection.set(properties("project-scm-developer-connection"))
                    }
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(properties("java-version"))) // "8"
    }
}

tasks.wrapper {
    gradleVersion = properties("gradle-version")
}