package com.example.domain.repository

import com.example.data.model.requests.FavoriteParkingModel

interface FavoriteParkingRepository {
    suspend fun getAllFavoriteParks(): List<FavoriteParkingModel>

    suspend fun getFavoriteParkingById(userId: Int, parkingId: Int): FavoriteParkingModel?

    suspend fun insertFavoriteParking(favoriteParkingModel: FavoriteParkingModel)

    suspend fun updateFavoriteParking(userId: Int, parkingId: Int, favoriteParkingModel: FavoriteParkingModel)

    suspend fun deleteFavoriteParking(userId: Int, parkingId: Int)
}