package com.example.plugins

import com.example.authentication.JwtService
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(jwtService: JwtService, userUseCase: UserUseCase) {
    authentication {
        jwt(name = "jwt") {
            verifier(verifier = jwtService.getAccessVerifier())
            realm = "Parko server"
            validate {
                val userEmail = it.payload.getClaim("email").asString()
                val user = userUseCase.getUserByEmail(userEmail = userEmail)
                user
            }
        }
    }
}