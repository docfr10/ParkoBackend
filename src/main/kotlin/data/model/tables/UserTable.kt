package com.example.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table(name = "user") {
    val id: Column<Int> = integer(name = "id").autoIncrement()
    val email: Column<String> = varchar(name = "email", length = 100).uniqueIndex()
    val password: Column<String> = varchar(name = "password", length = 50)
    val firstName: Column<String> = varchar(name = "first_name", length = 30)
    val lastName: Column<String> = varchar(name = "last_name", length = 30)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}