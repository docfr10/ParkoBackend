package com.example

import com.example.Database.configureDatabases
import com.example.authentication.JwtService
import com.example.data.model.repository.ParkingRepositoryImp
import com.example.data.model.repository.UserRepositoryImp
import com.example.domain.usecase.ParkingUseCase
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(factory = Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepositoryImp = UserRepositoryImp()
    val parkingRepositoryImp = ParkingRepositoryImp()

    val userUseCase = UserUseCase(userRepositoryImp = userRepositoryImp, jwtService = jwtService)
    val parkingUseCase = ParkingUseCase(parkingRepositoryImp = parkingRepositoryImp)

    configureDatabases()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase = userUseCase)
    // configureRouting()
}
