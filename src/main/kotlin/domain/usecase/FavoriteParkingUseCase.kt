package com.example.domain.usecase

import com.example.data.model.requests.FavoriteParkingModel
import com.example.data.repositoryimp.FavoriteParkingRepositoryImp

class FavoriteParkingUseCase(private val favoriteParkingRepositoryImp: FavoriteParkingRepositoryImp) {
    suspend fun getAllFavoriteParks(): List<FavoriteParkingModel> = favoriteParkingRepositoryImp.getAllFavoriteParks()

    suspend fun getFavoriteParkingById(userId: Int, parkingId: Int): FavoriteParkingModel? =
        favoriteParkingRepositoryImp.getFavoriteParkingById(userId = userId, parkingId = parkingId)

    suspend fun insertFavoriteParking(favoriteParkingModel: FavoriteParkingModel) {
        favoriteParkingRepositoryImp.insertFavoriteParking(favoriteParkingModel = favoriteParkingModel)
    }

    suspend fun updateFavoriteParking(userId: Int, parkingId: Int, favoriteParkingModel: FavoriteParkingModel) {
        favoriteParkingRepositoryImp.updateFavoriteParking(
            userId = userId,
            parkingId = parkingId,
            favoriteParkingModel = favoriteParkingModel
        )
    }

    suspend fun deleteFavoriteParking(userId: Int, parkingId: Int) {
        favoriteParkingRepositoryImp.deleteFavoriteParking(userId = userId, parkingId = parkingId)
    }
}