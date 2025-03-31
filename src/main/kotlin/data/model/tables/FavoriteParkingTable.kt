package com.example.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FavoriteParkingTable : Table(name = "user_favorite_parking") {
    val userId: Column<Int> = reference(name = "user_id", refColumn = UserTable.id)
    val parkingId: Column<Int> = reference(name = "parking_id", refColumn = ParkingTable.id)

    override val primaryKey: PrimaryKey = PrimaryKey(columns = arrayOf(userId, parkingId), name = "id")
}