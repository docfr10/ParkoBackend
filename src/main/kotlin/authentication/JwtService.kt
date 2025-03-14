package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.model.tables.UserModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {
    private val issuer = "parko-server"
    private val jwtSecretKey = System.getenv("JWT_SERVER")
    private val algorithm = Algorithm.HMAC512(jwtSecretKey)

    private val jwtVerifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun generateToken(userModel: UserModel): String =
        JWT.create()
            .withSubject("ParkoAppAuthentication")
            .withIssuer(issuer)
            .withClaim("email", userModel.email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)

    fun getVerifier(): JWTVerifier = jwtVerifier
}