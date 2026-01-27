package com.syntheticvideo.org.config

import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {

    val userDatabaseUrl = System.getenv("AI_VIDEO_LIBRARY_INGESTION_SERVICE_URL")
        ?: "jdbc:postgresql://ai_video_library_video_db:5432/ai_video_library_ingesion_service_db"
    val userDatabasePassword =
        System.getenv("AI_VIDEO_LIBRARY_INGESTION_SERVICE_PASSWORD") ?: "video_service_password_123"
    val userDatabaseUsername = System.getenv("AI_VIDEO_LIBRARY_INGESTION_SERVICE_USER") ?: "video_service_user"

    var connected = false

    // attempt to connect to the database with retries
    while (!connected) {
        try {
            Database.connect(
                url = userDatabaseUrl,
                user = userDatabaseUsername,
                password = userDatabasePassword
            )

            connected = true

            log.info("✅ DB connected & schema ready.")

        } catch (ex: Exception) {
            log.warn("⏳ DB not ready yet (${ex.message}), retrying in 5 sec...")
            Thread.sleep(5000)
            continue
        }

    }

}
