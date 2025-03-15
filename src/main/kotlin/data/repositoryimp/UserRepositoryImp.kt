package com.example.data.repositoryimp

import com.example.plugins.Database
import com.example.data.model.tables.UserModel
import com.example.data.model.tables.UserTable
import com.example.domain.repository.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserRepositoryImp : UserRepository {
    override suspend fun getUserByEmail(userEmail: String): UserModel? =
        Database.dbQuery {
            UserTable
                .selectAll()
                .where { UserTable.email.eq(userEmail) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }

    override suspend fun insertUser(userModel: UserModel) {
        Database.dbQuery {
            UserTable.insert { table ->
                table[email] = userModel.email
                table[login] = userModel.login
                table[password] = userModel.password
                table[firstName] = userModel.firstName
                table[lastName] = userModel.lastName
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null)
            return null

        return UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            login = row[UserTable.login],
            password = row[UserTable.password],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
        )
    }
}