package ru.mirea.shylit.studydeadline.data.tables

import org.jetbrains.exposed.v1.core.Table

object SubjectsTable : Table("subjects") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val name = varchar("name", 255)
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}