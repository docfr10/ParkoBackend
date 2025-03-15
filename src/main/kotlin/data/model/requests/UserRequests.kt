package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActivate: Boolean = false
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)