package ru.mirea.shylit.studydeadline.data.tables

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.date

object TasksTable : Table("tasks") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val subjectId = integer("subject_id").references(SubjectsTable.id)

    val title = varchar("title", 255)
    val description = text("description")
    val deadline = date("deadline")

    val status = varchar("status", 50)
    val priority = varchar("priority", 50)
    val type = varchar("type", 50)

    override val primaryKey = PrimaryKey(id)
}