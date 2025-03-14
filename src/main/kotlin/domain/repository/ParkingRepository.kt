package com.example.domain.repository

import com.example.data.model.tables.ParkingModel

interface ParkingRepository {
    suspend fun getAllParks(): List<ParkingModel>

    suspend fun getParkingById(parkingId: Int): ParkingModel?

    suspend fun insertParking(parkingModel: ParkingModel)

    suspend fun updateParking(parkingId: Int, parkingModel: ParkingModel)

    suspend fun deleteParking(parkingId: Int)
}