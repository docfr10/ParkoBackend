package com.example

import com.example.authentication.JwtService
import com.example.data.model.repository.UserRepositoryImp
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val jwtService = JwtService()
    val userRepositoryImp = UserRepositoryImp()
    val userUseCase = UserUseCase(userRepositoryImp = userRepositoryImp, jwtService = jwtService)

    authentication {
        jwt(name = "jwt") {
            verifier(verifier = jwtService.getVerifier())
            realm = "Parko server"
            validate {
                val userEmail = it.payload.getClaim("email").asString()
                val user = userUseCase.getUserByEmail(userEmail = userEmail)
                user
            }
        }
    }
}
