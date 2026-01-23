package com.ailibrary.org.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserSaveDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
@Serializable
data class UserUpdateDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)

@Serializable
data class UserDeleteDTO(
    val id: Int,
    val email: String
)

@Serializable
data class UserLoginDTO(
    val email: String,
    val password: String
)

@Serializable
data class UserChangePasswordDTO(
    val id: Int,
    val email: String,
    val newPassword: String
)
@Serializable
data class UserStatusChangeDTO(
    val id: Int,
    val status: String
)