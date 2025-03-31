package com.example.data.repositoryimp

import com.example.data.model.requests.ParkingModel
import com.example.data.model.tables.ParkingTable
import com.example.domain.repository.ParkingRepository
import com.example.plugins.Database
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ParkingRepositoryImp : ParkingRepository {
    override suspend fun getAllParks(): List<ParkingModel> =
        Database.dbQuery { ParkingTable.selectAll().mapNotNull { rowToParking(row = it) } }

    override suspend fun getParkingById(parkingId: Int): ParkingModel? =
        Database.dbQuery {
            ParkingTable.selectAll()
                .where { ParkingTable.id.eq(parkingId) }
                .map { rowToParking(row = it) }
                .singleOrNull()
        }

    override suspend fun insertParking(parkingModel: ParkingModel) {
        Database.dbQuery {
            ParkingTable.insert { table ->
                table[name] = parkingModel.name
                table[address] = parkingModel.address
                table[description] = parkingModel.description
                table[parkingLots] = parkingModel.parkingLots
            }
        }
    }

    override suspend fun updateParking(parkingId: Int, parkingModel: ParkingModel) {
        Database.dbQuery {
            ParkingTable.update(where = { ParkingTable.id.eq(parkingId) }) { table ->
                table[id] = parkingId
                table[name] = parkingModel.name
                table[address] = parkingModel.address
                table[description] = parkingModel.description
                table[parkingLots] = parkingModel.parkingLots
            }
        }
    }

    override suspend fun deleteParking(parkingId: Int) {
        Database.dbQuery { ParkingTable.deleteWhere(op = { id.eq(parkingId) }) }
    }

    private fun rowToParking(row: ResultRow?): ParkingModel? {
        if (row == null)
            return null

        return ParkingModel(
            id = row[ParkingTable.id],
            name = row[ParkingTable.name],
            address = row[ParkingTable.address],
            description = row[ParkingTable.description],
            parkingLots = row[ParkingTable.parkingLots]
        )
    }
}