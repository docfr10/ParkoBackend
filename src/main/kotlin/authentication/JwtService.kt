package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.model.requests.UserModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {
    private val issuer = "parko-server"
    private val jwtSecretKey = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecretKey)

    fun getAccessVerifier(): JWTVerifier =
        JWT.require(algorithm)
            .withIssuer(issuer)
            .withSubject("ParkoAppAccessToken")
            .build()

    fun generateAccessToken(userModel: UserModel): String =
        JWT.create()
            .withSubject("ParkoAppAccessToken")
            .withIssuer(issuer)
            .withClaim("email", userModel.email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)

    fun generateRefreshToken(userModel: UserModel): String =
        JWT.create()
            .withSubject("ParkoAppRefreshToken")
            .withIssuer(issuer)
            .withClaim("email", userModel.email)
            .withExpiresAt(LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.UTC))
            .sign(algorithm)

    fun isRefreshTokenValid(refreshToken: String): Boolean =
        try {
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withSubject("ParkoAppRefreshToken")
                .build()
                .verify(refreshToken)
            true
        } catch (e: Exception) {
            println(e)
            false
        }
}