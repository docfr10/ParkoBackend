package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.model.requests.UserModel

class JwtService {
    private val issuer = "parko-server"
    private val jwtSecretKey = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecretKey)

    private val jwtVerifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun generateToken(userModel: UserModel): String =
        JWT.create()
            .withSubject("ParkoAppAuthentication")
            .withIssuer(issuer)
            .withClaim("email", userModel.email)
            .sign(algorithm)

    fun getVerifier(): JWTVerifier = jwtVerifier
}