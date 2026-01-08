package com.ailibrary.org.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserSaveDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val passwordHash: String
)

@Serializable
data class UserLoginDTO(
    val email: String,
    val password: String
)