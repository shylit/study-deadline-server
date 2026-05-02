package ru.mirea.shylit.studydeadline.data.tables

import org.jetbrains.exposed.v1.core.Table

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val firebaseUid = varchar("firebase_uid", 128).uniqueIndex()
    val email = varchar("email", 255)
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)
}