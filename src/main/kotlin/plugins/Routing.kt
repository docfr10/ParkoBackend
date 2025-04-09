package com.example.plugins

import com.example.authentication.JwtService
import com.example.domain.usecase.FavoriteParkingUseCase
import com.example.domain.usecase.ParkingUseCase
import com.example.domain.usecase.UserUseCase
import com.example.routes.favoriteParkingRoute
import com.example.routes.parkingRoute
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    parkingUseCase: ParkingUseCase,
    favoriteParkingUseCase: FavoriteParkingUseCase,
    jwtService: JwtService
) {
    routing {
        userRoute(jwtService = jwtService, userUseCase = userUseCase)
        parkingRoute(parkingUseCase = parkingUseCase)
        favoriteParkingRoute(favoriteParkingUseCase = favoriteParkingUseCase)
    }
}