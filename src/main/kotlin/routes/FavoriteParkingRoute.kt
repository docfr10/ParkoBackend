package com.example.routes

import com.example.data.model.requests.FavoriteParkingModel
import com.example.data.model.requests.FavoriteParkingRequest
import com.example.data.model.requests.UserModel
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.FavoriteParkingUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.favoriteParkingRoute(favoriteParkingUseCase: FavoriteParkingUseCase) {
    authenticate("jwt") {
        get(path = "api/v1/get-all-favorite-parks") {
            val user = call.principal<UserModel>() ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    BaseResponse(success = false, message = Constants.Error.GENERAL)
                )
                return@get
            }
            try {
                val favoriteParks = favoriteParkingUseCase.getFavoriteParksByUserId(userId = user.id)
                call.respond(status = HttpStatusCode.OK, message = favoriteParks)
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }

        post(path = "api/v1/create-favorite-parking") {
            val user = call.principal<UserModel>() ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    BaseResponse(success = false, message = Constants.Error.GENERAL)
                )
                return@post
            }
            val request = call.receiveNullable<FavoriteParkingRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
                )
                return@post
            }

            try {
                favoriteParkingUseCase.insertFavoriteParking(
                    favoriteParkingModel = FavoriteParkingModel(
                        userId = user.id,
                        parkingId = request.parkingId
                    )
                )
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = true, message = Constants.Success.FAVORITE_PARKING_ADDED)
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }

        delete(path = "api/v1/delete-favorite-parking") {
            val user = call.principal<UserModel>() ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    BaseResponse(success = false, message = Constants.Error.GENERAL)
                )
                return@delete
            }
            val request = call.receiveNullable<FavoriteParkingRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
                )
                return@delete
            }

            try {
                favoriteParkingUseCase.deleteFavoriteParking(
                    userId = user.id,
                    parkingId = request.parkingId
                )
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = true, message = Constants.Success.FAVORITE_PARKING_DELETED)
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }
    }
}
