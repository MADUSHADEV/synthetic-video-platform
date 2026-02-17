package com.syntheticvideo.org.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.net.url.Url
import io.ktor.server.application.Application
import kotlinx.coroutines.runBlocking

fun Application.configureMinio() {
    val endpoint = environment.config.propertyOrNull("minio.endpoint")?.getString() ?: System.getenv("MINIO_ENDPOINT")
    ?: "http://localhost:9000"
    val accessKey = environment.config.propertyOrNull("minio.access")?.getString() ?: System.getenv("MINIO_ROOT_USER")
    ?: "minioadmin"
    val secretKey =
        environment.config.propertyOrNull("minio.secret")?.getString() ?: System.getenv("MINIO_ROOT_PASSWORD")
        ?: "minioadmin"

    runBlocking {
        S3Client.fromEnvironment {
            this.region = "us-east-1" // MinIO doesn't require a specific region
            this.endpointUrl = Url.parse(endpoint)
            this.credentialsProvider = StaticCredentialsProvider {
                accessKeyId = accessKey
                secretAccessKey = secretKey
            }
        }
    }
}