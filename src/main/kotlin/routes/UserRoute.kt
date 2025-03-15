package com.example.routes

import com.example.authentication.hashPassword
import com.example.data.model.requests.LoginRequest
import com.example.data.model.requests.UserRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.tables.UserModel
import com.example.domain.usecase.UserUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(userUseCase: UserUseCase) {
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
                login = registerRequest.login.trim().lowercase(),
                password = hashPassword(password = registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
            )

            userUseCase.insertUser(userModel = userModel)
            call.respond(
                status = HttpStatusCode.OK,
                BaseResponse(success = true, message = userUseCase.generateToken(userModel = userModel))
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Conflict,
                BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
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
            val foundUser = userUseCase.getUserByEmail(userEmail = loginRequest.email.trim().lowercase())

            when {
                foundUser == null -> call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(success = false, message = Constants.Error.WRONG_EMAIL)
                )

                foundUser.password == hashPassword(password = loginRequest.password) -> call.respond(
                    status = HttpStatusCode.OK,
                    message = userUseCase.generateToken(userModel = foundUser)
                )

                else -> call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(success = false, message = Constants.Error.INCORRECT_PASSWORD)
                )
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Conflict,
                message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
            )
        }
    }
}