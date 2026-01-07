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
    implementation(Ktor.ServerCore)
    implementation(Ktor.contentNegotiation)
    implementation(Ktor.ServerAuth)
    implementation(Ktor.ServerAuthJwt)
    implementation(Ktor.Resilience4jKotlin)
    implementation(Ktor.Resilience4jCircuitBreaker)
    implementation(Ktor.Resilience4jCore)
    implementation(Ktor.Resilience4jRetry)
    implementation(Ktor.SerializationKotlinxJson)
    implementation(Ktor.LogbackClassic)
    implementation(Ktor.LettuceCore)
//    implementation("io.ktor:ktor-server-auth:3.3.2")
//    implementation("io.ktor:ktor-server-core:3.3.2")
//    implementation("io.ktor:ktor-server-core:3.3.2")
//    implementation("io.ktor:ktor-server-auth-jwt:3.3.2")
//    implementation("io.ktor:ktor-server-auth:3.3.2")
//    implementation("io.ktor:ktor-server-core:3.3.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}