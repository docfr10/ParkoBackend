package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)

@Serializable
data class UserRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val refreshToken: String? = null
)