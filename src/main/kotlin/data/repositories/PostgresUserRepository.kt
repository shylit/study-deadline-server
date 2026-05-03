package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.UsersTable
import ru.mirea.shylit.studydeadline.domain.models.User
import ru.mirea.shylit.studydeadline.domain.repositories.UserRepository

class PostgresUserRepository : UserRepository {

    override fun getCurrentUser(): User {
        return transaction {
            val userId = getOrCreateDemoUser()

            UsersTable
                .selectAll()
                .where { UsersTable.id eq userId }
                .map { row ->
                    User(
                        id = row[UsersTable.id],
                        firebaseUid = row[UsersTable.firebaseUid],
                        email = row[UsersTable.email],
                        name = row[UsersTable.name]
                    )
                }
                .single()
        }
    }

    private fun getOrCreateDemoUser(): Int {
        val existingUser = UsersTable
            .selectAll()
            .where { UsersTable.firebaseUid eq "demo-firebase-uid" }
            .map { row -> row[UsersTable.id] }
            .singleOrNull()

        if (existingUser != null) {
            return existingUser
        }

        return UsersTable.insert { row ->
            row[UsersTable.firebaseUid] = "demo-firebase-uid"
            row[UsersTable.email] = "student@example.com"
            row[UsersTable.name] = "Студент"
        } get UsersTable.id
    }
}