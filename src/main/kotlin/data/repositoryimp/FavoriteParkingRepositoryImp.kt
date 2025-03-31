package com.example.data.repositoryimp

import com.example.data.model.requests.FavoriteParkingModel
import com.example.data.model.tables.FavoriteParkingTable
import com.example.domain.repository.FavoriteParkingRepository
import com.example.plugins.Database
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class FavoriteParkingRepositoryImp : FavoriteParkingRepository {
    override suspend fun getAllFavoriteParks(): List<FavoriteParkingModel> =
        Database.dbQuery { FavoriteParkingTable.selectAll().mapNotNull { rowToFavoriteParking(row = it) } }

    override suspend fun getFavoriteParkingById(userId: Int, parkingId: Int): FavoriteParkingModel? = Database.dbQuery {
        FavoriteParkingTable.selectAll()
            .where { FavoriteParkingTable.userId.eq(userId) and FavoriteParkingTable.parkingId.eq(parkingId) }
            .map { rowToFavoriteParking(row = it) }
            .singleOrNull()
    }

    override suspend fun insertFavoriteParking(favoriteParkingModel: FavoriteParkingModel) {
        Database.dbQuery {
            FavoriteParkingTable.insert { table ->
                table[userId] = favoriteParkingModel.userId
                table[parkingId] = favoriteParkingModel.parkingId
            }
        }
    }

    override suspend fun updateFavoriteParking(
        userId: Int,
        parkingId: Int,
        favoriteParkingModel: FavoriteParkingModel
    ) {
        Database.dbQuery {
            FavoriteParkingTable.update(where = {
                FavoriteParkingTable.userId.eq(userId) and FavoriteParkingTable.parkingId.eq(parkingId)
            }) { table ->
                table[FavoriteParkingTable.userId] = favoriteParkingModel.userId
                table[FavoriteParkingTable.parkingId] = favoriteParkingModel.parkingId
            }
        }
    }

    override suspend fun deleteFavoriteParking(userId: Int, parkingId: Int) {
        Database.dbQuery {
            FavoriteParkingTable.deleteWhere(op = {
                FavoriteParkingTable.userId.eq(userId) and FavoriteParkingTable.parkingId.eq(parkingId)
            })
        }
    }

    private fun rowToFavoriteParking(row: ResultRow?): FavoriteParkingModel? {
        if (row == null)
            return null

        return FavoriteParkingModel(
            userId = row[FavoriteParkingTable.userId],
            parkingId = row[FavoriteParkingTable.parkingId]
        )
    }
}