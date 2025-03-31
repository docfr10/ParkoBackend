package com.example.routes

import com.example.data.model.requests.FavoriteParkingModel
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
            try {
                val favoriteParks = favoriteParkingUseCase.getAllFavoriteParks()
                call.respond(status = HttpStatusCode.OK, message = favoriteParks)
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }
    }

    get(path = "api/v1/get-favorite-parking") {
        val userId = call.parameters["userId"]?.toIntOrNull() ?: run {
            call.respond(
                HttpStatusCode.BadRequest,
                BaseResponse(success = false, message = Constants.Error.GENERAL)
            )
            return@get
        }
        val parkingId = call.parameters["parkingId"]?.toIntOrNull() ?: run {
            call.respond(
                HttpStatusCode.BadRequest,
                BaseResponse(success = false, message = Constants.Error.GENERAL)
            )
            return@get
        }

        try {
            val parking = favoriteParkingUseCase.getFavoriteParkingById(userId = userId, parkingId = parkingId)

            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(success = true, message = parking.toString())
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
            )
        }
    }

    post(path = "api/v1/create-favorite-parking") {
        val addFavoriteParkingRequest = call.receiveNullable<FavoriteParkingModel>() ?: run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
            )
            return@post
        }

        try {
            val favoriteParkingModel = FavoriteParkingModel(
                userId = addFavoriteParkingRequest.userId,
                parkingId = addFavoriteParkingRequest.parkingId
            )

            favoriteParkingUseCase.insertFavoriteParking(favoriteParkingModel = favoriteParkingModel)
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

    post(path = "api/v1/update-favorite-parking") {
        val updateFavoriteParkingRequest = call.receiveNullable<FavoriteParkingModel>() ?: run {
            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
            )
            return@post
        }

        try {
            val updateFavoriteParkingModel = FavoriteParkingModel(
                userId = updateFavoriteParkingRequest.userId,
                parkingId = updateFavoriteParkingRequest.parkingId
            )

            favoriteParkingUseCase.updateFavoriteParking(
                userId = updateFavoriteParkingRequest.userId,
                parkingId = updateFavoriteParkingModel.parkingId,
                favoriteParkingModel = updateFavoriteParkingModel
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(success = true, message = Constants.Success.FAVORITE_PARKING_UPDATED)
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Conflict,
                BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
            )
        }
    }

    delete(path = "api/v1/delete-favorite-parking") {
        val deleteFavoriteParkingRequest = call.receiveNullable<FavoriteParkingModel>() ?: run {
            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
            )
            return@delete
        }

        try {
            favoriteParkingUseCase.deleteFavoriteParking(
                userId = deleteFavoriteParkingRequest.userId,
                parkingId = deleteFavoriteParkingRequest.parkingId
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