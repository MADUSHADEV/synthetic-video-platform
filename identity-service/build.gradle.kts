import buildsrc.convention.Ktor

plugins {
    alias(libs.plugins.ktor)
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.ailibrary.org"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))

    implementation(Ktor.contentNegotiation)
    implementation(Ktor.ServerStatusPages)
    implementation(Ktor.ServerHostCommon)
    implementation(Ktor.PostgreSQL)
    implementation(Ktor.ExposedCore)
    implementation(Ktor.ExposedJdbc)
    implementation(Ktor.ExposedDao)
    implementation(Ktor.KoinKtor)
    implementation(Ktor.KoinLoggerSlf4j)
    implementation(Ktor.JBCrypt)
    implementation(Ktor.Valiktor)
    implementation(Ktor.RequestValidation)
    implementation(Ktor.Resource)
    implementation(Ktor.Session)
    implementation(Ktor.KafkaClients)
    implementation(Ktor.LettuceCore)
    implementation(Ktor.MicrometerKtorMetrics)
    implementation(Ktor.MicrometerBom)
    implementation(Ktor.MicrometerRegistryPrometheus)
    implementation(Ktor.MicrometerCore)



    implementation(Ktor.SeverNetty)
    implementation(Ktor.ServerConfig)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}