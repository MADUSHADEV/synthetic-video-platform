package com.ailibrary.org.sharedDTOs

import kotlinx.serialization.Serializable

@Serializable
data class UserRespondDTO(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val token: String? = "Token will be generated after login"
)