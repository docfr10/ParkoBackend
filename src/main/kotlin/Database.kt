package com.example

import com.example.data.model.tables.ParkingTable
import com.example.data.model.tables.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

// Database configuration object
object Database {
    // Database params
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbURL = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_POSTGRES_PASSWORD")

    // Initialization of database
    fun Application.configureDatabases() {
        println(message = "DB_URL is: $dbURL")
        println(message = "DB_USER is: $dbUser")

        // Connect to database
        Database.connect(datasource = getHikariDatasource())

        // Create tables
        transaction {
            SchemaUtils.create(tables = arrayOf(UserTable, ParkingTable))
        }
    }

    private fun getHikariDatasource(): HikariDataSource =
        HikariDataSource(
            HikariConfig().apply {
                // Driver for connection PostgreSQL
                driverClassName = "org.postgresql.Driver"
                jdbcUrl = dbURL
                username = dbUser
                password = dbPassword
                // Maximum database connections
                maximumPoolSize = 3
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            }
        )
}
