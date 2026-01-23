package com.ailibrary.org.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val passwordHash: String,
    val status: String? = null
)
