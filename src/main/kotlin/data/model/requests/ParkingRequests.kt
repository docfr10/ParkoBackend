package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddParkingRequest(
    val name: String,
    val address: String,
    val description: String,
    val parkingLots: Int
)

@Serializable
data class UpdateParkingRequest(
    val id: Int,
    val name: String,
    val address: String,
    val description: String,
    val parkingLots: Int
)