package com.example.data.model.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ParkingTable : Table() {
    val id: Column<Int> = integer(name = "id").autoIncrement()
    val name: Column<String> = varchar(name = "name", length = 100)
    val address: Column<String> = varchar(name = "address", length = 100)
    val description: Column<String> = varchar(name = "description", length = 500)
    val parkingLots: Column<Int> = integer(name = "parking_lots")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

@Serializable
data class ParkingModel(
    val id: Int,
    val name: String,
    val address: String,
    val description: String,
    val parkingLots: Int
)