import buildsrc.convention.Ktor

plugins {
    kotlin("jvm")
    id("org.liquibase.gradle") version "2.2.0"
}

group = "com.ailibrary.org"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Ktor.LiquibaseCore)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}