package com.ailibrary.org.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage

fun Application.configureRespondExceptionHandler() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<ConstraintViolationException> { call, cause ->
            val errors = cause.constraintViolations
                .mapToMessage(baseName = "messages", locale = java.util.Locale.ENGLISH)
                .map { violation ->
                    mapOf("property" to violation.property, "message" to violation.message)
                }
            call.respond(HttpStatusCode.BadRequest, mapOf("errors" to errors))
        }
    }

}