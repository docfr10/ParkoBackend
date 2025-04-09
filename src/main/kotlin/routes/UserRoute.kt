package com.example.routes

import com.auth0.jwt.JWT
import com.example.authentication.JwtService
import com.example.authentication.hashPassword
import com.example.data.model.requests.LoginRequest
import com.example.data.model.requests.UserModel
import com.example.data.model.requests.UserRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TokenResponse
import com.example.domain.usecase.UserUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(jwtService: JwtService, userUseCase: UserUseCase) {
    post(path = "api/v1/signup") {
        val registerRequest = call.receiveNullable<UserRequest>() ?: run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = BaseResponse(success = false, message = Constants.Error.GENERAL)
            )
            return@post
        }

        try {
            val userModel = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                password = hashPassword(password = registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
            )

            userUseCase.insertUser(userModel = userModel)
            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(success = true, message = Constants.Success.USER_REGISTERED)
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Conflict,
                message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
            )
        }
    }

    post(path = "api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = BaseResponse(success = false, message = Constants.Error.GENERAL)
            )
            return@post
        }

        try {
            if (!loginRequest.refreshToken.isNullOrBlank()) {
                if (!jwtService.isRefreshTokenValid(loginRequest.refreshToken)) {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = BaseResponse(success = false, message = Constants.Error.INCORRECT_TOKEN)
                    )
                    return@post
                } else {
                    val foundUserByToken = userUseCase.getUserByEmail(
                        userEmail = JWT.decode(loginRequest.refreshToken).getClaim("email").toString()
                    ) ?: run {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = BaseResponse(success = false, message = Constants.Error.USER_NOT_FOUND_BY_TOKEN)
                        )
                        return@post
                    }

                    call.respond(
                        status = HttpStatusCode.OK,
                        message = TokenResponse(
                            accessToken = jwtService.generateAccessToken(foundUserByToken),
                            refreshToken = jwtService.generateRefreshToken(foundUserByToken)
                        )
                    )
                    return@post
                }
            } else {
                val foundUser = userUseCase.getUserByEmail(userEmail = loginRequest.email.trim().lowercase())

                when {
                    foundUser == null -> call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = BaseResponse(success = false, message = Constants.Error.WRONG_EMAIL)
                    )

                    foundUser.password == hashPassword(password = loginRequest.password) -> call.respond(
                        status = HttpStatusCode.OK,
                        message = TokenResponse(
                            accessToken = jwtService.generateAccessToken(foundUser),
                            refreshToken = jwtService.generateRefreshToken(foundUser)
                        )
                    )

                    else -> call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = BaseResponse(success = false, message = Constants.Error.INCORRECT_PASSWORD)
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Conflict,
                message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
            )
        }
    }
}