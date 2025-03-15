package com.example.plugins

import com.example.domain.usecase.ParkingUseCase
import com.example.domain.usecase.UserUseCase
import com.example.routes.parkingRoute
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userUseCase: UserUseCase, parkingUseCase: ParkingUseCase) {
    routing {
        userRoute(userUseCase = userUseCase)
        parkingRoute(parkingUseCase = parkingUseCase)
    }
}