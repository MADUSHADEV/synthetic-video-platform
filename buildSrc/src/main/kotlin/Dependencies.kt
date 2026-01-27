package buildsrc.convention

object Versions {
    const val kotlin = "3.2.0"
    const val ktor = "3.3.2"
    const val logbackClassic = "1.4.14"
    const val resilience4j = "2.3.0"
    const val exposed = "0.61.0"
    const val h2 = "2.3.232"
    const val postgresql = "42.7.7"
    const val byCrypt = "0.4"
    const val kafkaClient = "3.9.1"
    const val micrometer = "1.14.5"
    const val valiktor = "0.12.0"
    const val koin = "4.1.2-Beta1"
    const val liquibase = "4.27.0"
    const val redis = "6.7.1.RELEASE"
    const val azureSDK = "1.3.0"
}

object Ktor {

    // Core dependencies
    const val contentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"
    const val ServerCore = "io.ktor:ktor-server-core:${Versions.ktor}"
    const val SerializationKotlinxJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

    // Server netty dependency
    const val SeverNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    const val ServerConfig = "io.ktor:ktor-server-config-yaml:${Versions.ktor}"
    const val ServerStatusPages = "io.ktor:ktor-server-status-pages:${Versions.ktor}"
    const val ServerHostCommon = "io.ktor:ktor-server-host-common:${Versions.ktor}"

    // Authentication dependencies
    const val ServerAuth = "io.ktor:ktor-server-auth:${Versions.ktor}"
    const val ServerAuthJwt = "io.ktor:ktor-server-auth-jwt:${Versions.ktor}"

    // ORMs dependencies
    const val ExposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
    const val ExposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    const val ExposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"

    // H2 Database dependency
    const val H2Database = "com.h2database:h2:${Versions.h2}"

    // PostgreSQL Database dependency
    const val PostgreSQL = "org.postgresql:postgresql:${Versions.postgresql}"

    // Resilience4j
    const val Resilience4jKotlin = "io.github.resilience4j:resilience4j-kotlin:${Versions.resilience4j}"
    const val Resilience4jRetry = "io.github.resilience4j:resilience4j-retry:${Versions.resilience4j}"
    const val Resilience4jCircuitBreaker = "io.github.resilience4j:resilience4j-circuitbreaker:${Versions.resilience4j}"
    const val Resilience4jCore = "io.github.resilience4j:resilience4j-core:${Versions.resilience4j}"

    // Logging
    const val LogbackClassic = "ch.qos.logback:logback-classic:${Versions.logbackClassic}"

    // Dependency Injection
    const val KoinKtor = "io.insert-koin:koin-ktor:${Versions.koin}"
    const val KoinLoggerSlf4j = "io.insert-koin:koin-logger-slf4j:${Versions.koin}"

    // Cryptography
    const val JBCrypt = "org.mindrot:jbcrypt:${Versions.byCrypt}"

    // Validation
    const val Valiktor = "org.valiktor:valiktor-core:${Versions.valiktor}"
    const val RequestValidation = "io.ktor:ktor-server-request-validation:${Versions.ktor}"

    // Resources & Routing
    const val Resource = "io.ktor:ktor-server-resources:${Versions.ktor}"

    // Sessions
    const val Session = "io.ktor:ktor-server-sessions:${Versions.ktor}"

    // Kafka
    const val KafkaClients = "org.apache.kafka:kafka-clients:${Versions.kafkaClient}"

    // Redis
    const val LettuceCore = "io.lettuce:lettuce-core:${Versions.redis}"

    // Micrometer & Prometheus
    const val MicrometerKtorMetrics = "io.ktor:ktor-server-metrics-micrometer:${Versions.ktor}"
    const val MicrometerBom = "io.micrometer:micrometer-bom:${Versions.micrometer}"
    const val MicrometerRegistryPrometheus = "io.micrometer:micrometer-registry-prometheus:${Versions.micrometer}"
    const val MicrometerCore = "io.micrometer:micrometer-core:${Versions.micrometer}"

    // Liquibase
    const val LiquibaseCore = "org.liquibase:liquibase-core:${Versions.liquibase}"

    // Azure SDK BOM and dependencies
    const val AzureSDK = "com.azure:azure-sdk-bom:${Versions.azureSDK}"
    const val BlobStorage = "com.azure:azure-storage-blob"
    const val Identity = "com.azure:azure-identity"

}