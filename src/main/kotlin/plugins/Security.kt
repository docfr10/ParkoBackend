package com.example.plugins

import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(userUseCase: UserUseCase) {
    authentication {
        jwt(name = "jwt") {
            verifier(verifier = userUseCase.getGwtVerifier())
            realm = "Parko server"
            validate {
                val userEmail = it.payload.getClaim("email").asString()
                val user = userUseCase.getUserByEmail(userEmail = userEmail)
                user
            }
        }
    }
}