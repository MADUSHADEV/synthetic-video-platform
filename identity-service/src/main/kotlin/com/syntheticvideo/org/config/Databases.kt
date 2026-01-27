package com.syntheticvideo.org.config

import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {

    val userDatabaseUrl = System.getenv("AI_VIDEO_LIBRARY_IDENTITY_SERVICE_URL")
        ?: "jdbc:postgresql://db:5432/ai_video_library_identity_service_db"
    val userDatabasePassword =
        System.getenv("AI_VIDEO_LIBRARY_IDENTITY_SERVICE_PASSWORD") ?: "identity_service_password_123"
    val userDatabaseUsername = System.getenv("AI_VIDEO_LIBRARY_IDENTITY_SERVICE_USER") ?: "identity_service_user"

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
