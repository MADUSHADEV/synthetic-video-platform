package com.ailibrary.org.protocol

import kotlinx.serialization.Serializable

@Serializable
data class ResponseProtocol<T>(
    val status: String,
    val code: Int,
    val message: String?,
    val data: T? = null,
    val errors: List<Map<String, String?>> = emptyList()
) {
    companion object {
        fun <T> success(
            data: T? = null,
            message: String? = "Success",
            code: Int = 200
        ): ResponseProtocol<T> {
            return ResponseProtocol(
                status = "success",
                code = code,
                message = message,
                data = data
            )
        }

        fun failed(
            message: String? = "Error",
            code: Int = 400,
            errors: List<Map<String, String?>> = emptyList()
        ): ResponseProtocol<Nothing> {
            return ResponseProtocol(
                status = "error",
                code = code,
                message = message,
                errors = errors
            )
        }
    }
}
