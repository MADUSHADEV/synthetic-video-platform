import buildsrc.convention.Ktor

plugins {
    id("buildsrc.convention.kotlin-jvm")
}

group = "com.ailibrary.org"
version = "0.0.1"

repositories {
    mavenCentral()
}


dependencies {
    implementation(Ktor.contentNegotiation)
    implementation(Ktor.ServerCore)
    implementation(Ktor.SerializationKotlinxJson)
    implementation(Ktor.ServerAuth)
    implementation(Ktor.ServerAuthJwt)
    implementation(Ktor.Resilience4jKotlin)
    implementation(Ktor.Resilience4jCircuitBreaker)
    implementation(Ktor.Resilience4jCore)
    implementation(Ktor.Resilience4jRetry)
    implementation(Ktor.LogbackClassic)
    implementation(Ktor.LettuceCore)


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}