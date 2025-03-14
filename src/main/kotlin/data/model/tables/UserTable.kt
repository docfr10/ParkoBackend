package com.example.data.model.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val id: Column<Int> = integer(name = "id").autoIncrement()
    val email: Column<String> = varchar(name = "email", length = 100).uniqueIndex()
    val login: Column<String> = varchar(name = "login", length = 50).uniqueIndex()
    val password: Column<String> = varchar(name = "password", length = 50)
    val firstName: Column<String> = varchar(name = "first_name", length = 30)
    val lastName: Column<String> = varchar(name = "last_name", length = 30)
    val isActivated: Column<Boolean> = bool(name = "is_activated")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

@Serializable
data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActivated: Boolean = false
)