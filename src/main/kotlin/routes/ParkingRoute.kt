package com.example.routes

import com.example.data.model.requests.AddParkingRequest
import com.example.data.model.requests.UpdateParkingRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.tables.ParkingModel
import com.example.domain.usecase.ParkingUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parkingRoute(parkingUseCase: ParkingUseCase) {
    authenticate("jwt") {
        get(path = "api/v1/get-all-parks") {
            try {
                val parks = parkingUseCase.getAllParks()
                call.respond(status = HttpStatusCode.OK, message = parks)
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }

        post(path = "api/v1/create-parking") {
            val addParkingRequest = call.receiveNullable<AddParkingRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
                )
                return@post
            }

            try {
                val parkingModel = ParkingModel(
                    id = 0,
                    name = addParkingRequest.name,
                    address = addParkingRequest.address,
                    description = addParkingRequest.description,
                    parkingLots = addParkingRequest.parkingLots
                )

                parkingUseCase.insertParking(parkingModel = parkingModel)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = true, message = Constants.Success.PARKING_ADDED)
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }

        post(path = "api/v1/update-parking") {
            val updateParkingRequest = call.receiveNullable<UpdateParkingRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
                )
                return@post
            }

            try {
                val updateParkingModel = ParkingModel(
                    id = updateParkingRequest.id,
                    name = updateParkingRequest.name,
                    address = updateParkingRequest.address,
                    description = updateParkingRequest.description,
                    parkingLots = updateParkingRequest.parkingLots
                )

                parkingUseCase.updateParking(parkingId = updateParkingModel.id, parkingModel = updateParkingModel)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = true, message = Constants.Success.PARKING_UPDATED)
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    BaseResponse(success = false, message = e.message ?: Constants.Error.GENERAL)
                )
            }
        }

        delete(path = "api/v1/delete-parking") {
            val deleteParkingRequest = call.receiveNullable<ParkingModel>() ?: run {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = false, message = Constants.Error.MISSING_FIELDS)
                )
                return@delete
            }

            try {
                parkingUseCase.deleteParking(parkingId = deleteParkingRequest.id)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(success = true, message = Constants.Success.PARKING_DELETED)
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