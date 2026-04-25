package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteParkingModel(
    val userId: Int,
    val parkingId: Int
)

@Serializable
data class FavoriteParkingRequest(val parkingId: Int)