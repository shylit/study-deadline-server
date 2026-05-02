package ru.mirea.shylit.studydeadline.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.SubjectsTable
import ru.mirea.shylit.studydeadline.data.tables.TasksTable
import ru.mirea.shylit.studydeadline.data.tables.UsersTable

object DatabaseFactory {

    fun init() {
        Database.connect(hikariDataSource())

        transaction {
            SchemaUtils.create(
                UsersTable,
                SubjectsTable,
                TasksTable
            )
        }

        println("Database connection initialized")
    }

    private fun hikariDataSource(): HikariDataSource {
        val config = HikariConfig()

        config.jdbcUrl = System.getenv("DATABASE_URL")
            ?: "jdbc:postgresql://localhost:5432/study_deadline"

        config.username = System.getenv("DATABASE_USER")
            ?: "postgres"

        config.password = System.getenv("DATABASE_PASSWORD")
            ?: "postgres"

        config.driverClassName = "org.postgresql.Driver"
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        return HikariDataSource(config)
    }
}