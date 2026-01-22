package com.ailibrary.org.utils

import com.ailibrary.org.exception.BusinessException
import com.ailibrary.org.protocol.ResponseProtocol
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage

fun Application.configureRespondExceptionHandler() {

    install(StatusPages) {

        exception<ConstraintViolationException> { call, cause ->
            val errors =
                cause.constraintViolations.mapToMessage(baseName = "messages", locale = java.util.Locale.ENGLISH)
                    .map { violation ->
                        mapOf("property" to violation.property, "message" to violation.message)
                    }
            call.respond(
                HttpStatusCode.BadRequest,
                ResponseProtocol.failed(
                    message = "Validation failed",
                    code = HttpStatusCode.BadRequest.value,
                    errors = errors.map { it["message"] ?: "Invalid value" }
                )
            )
        }

        exception<BusinessException> { call, cause ->
            call.respond(
                cause.statusCode,
                ResponseProtocol.failed(
                    message = cause.message,
                    code = cause.statusCode.value
                )
            )
        }

        exception<Throwable> { call, cause ->
            println(
                "Internal server error: ${cause.message}, ${cause.stackTraceToString()}"
            )
            call.respond(
                HttpStatusCode.InternalServerError,
                ResponseProtocol.failed(
                    message = "Internal server error",
                    code = HttpStatusCode.InternalServerError.value
                )
            )
        }

    }

}