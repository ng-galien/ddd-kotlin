import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()


plugins {
    kotlin("jvm") version "1.7.10"
    jacoco
    `maven-publish`
    id("org.jetbrains.changelog") version "1.3.1"
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
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ng-galien/ddd-kotlin")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

changelog {
    version.set(properties("project-version"))
    groups.set(emptyList())
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = properties("java-version")
}

tasks.wrapper {
    gradleVersion = properties("gradle-version")
}