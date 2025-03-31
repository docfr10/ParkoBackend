package com.example.domain.usecase

import com.example.data.model.requests.ParkingModel
import com.example.data.repositoryimp.ParkingRepositoryImp

class ParkingUseCase(private val parkingRepositoryImp: ParkingRepositoryImp) {
    suspend fun getAllParks(): List<ParkingModel> = parkingRepositoryImp.getAllParks()

    suspend fun getParkingById(parkingId: Int): ParkingModel? =
        parkingRepositoryImp.getParkingById(parkingId = parkingId)

    suspend fun insertParking(parkingModel: ParkingModel) {
        parkingRepositoryImp.insertParking(parkingModel = parkingModel)
    }

    suspend fun updateParking(parkingId: Int, parkingModel: ParkingModel) {
        parkingRepositoryImp.updateParking(parkingId = parkingId, parkingModel = parkingModel)
    }

    suspend fun deleteParking(parkingId: Int) {
        parkingRepositoryImp.deleteParking(parkingId = parkingId)
    }
}