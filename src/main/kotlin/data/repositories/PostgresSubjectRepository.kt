package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import ru.mirea.shylit.studydeadline.data.tables.SubjectsTable
import ru.mirea.shylit.studydeadline.data.tables.UsersTable
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class PostgresSubjectRepository : SubjectRepository {

    override fun getAllSubjects(firebaseUid: String): List<Subject> {
        return transaction {
            val userId = getOrCreateUser(firebaseUid)

            SubjectsTable
                .selectAll()
                .where { SubjectsTable.userId eq userId }
                .map { row ->
                    Subject(
                        id = row[SubjectsTable.id],
                        name = row[SubjectsTable.name],
                        description = row[SubjectsTable.description]
                    )
                }
        }
    }

    override fun getSubjectById(
        firebaseUid: String,
        subjectId: Int
    ): Subject? {
        return transaction {
            val userId = getOrCreateUser(firebaseUid)

            SubjectsTable
                .selectAll()
                .where {
                    (SubjectsTable.id eq subjectId) and
                            (SubjectsTable.userId eq userId)
                }
                .map { row ->
                    Subject(
                        id = row[SubjectsTable.id],
                        name = row[SubjectsTable.name],
                        description = row[SubjectsTable.description]
                    )
                }
                .singleOrNull()
        }
    }

    override fun searchSubjects(
        firebaseUid: String,
        query: String?
    ): List<Subject> {
        return transaction {
            getAllSubjects(firebaseUid).filter { subject ->
                query.isNullOrBlank() ||
                        subject.name.contains(query, ignoreCase = true) ||
                        subject.description.contains(query, ignoreCase = true)
            }
        }
    }

    override fun createSubject(
        firebaseUid: String,
        name: String,
        description: String
    ): Subject {
        return transaction {
            val userId = getOrCreateUser(firebaseUid)

            val subjectId = SubjectsTable.insert { row ->
                row[SubjectsTable.userId] = userId
                row[SubjectsTable.name] = name
                row[SubjectsTable.description] = description
            } get SubjectsTable.id

            Subject(
                id = subjectId,
                name = name,
                description = description
            )
        }
    }

    override fun updateSubject(
        firebaseUid: String,
        subjectId: Int,
        name: String,
        description: String
    ): Subject? {
        return transaction {
            val userId = getOrCreateUser(firebaseUid)

            val updatedRows = SubjectsTable.update(
                where = {
                    (SubjectsTable.id eq subjectId) and
                            (SubjectsTable.userId eq userId)
                }
            ) { row ->
                row[SubjectsTable.name] = name
                row[SubjectsTable.description] = description
            }

            if (updatedRows == 0) {
                null
            } else {
                getSubjectById(
                    firebaseUid = firebaseUid,
                    subjectId = subjectId
                )
            }
        }
    }

    override fun deleteSubject(
        firebaseUid: String,
        subjectId: Int
    ): Boolean {
        return transaction {
            val userId = getOrCreateUser(firebaseUid)

            val deletedRows = SubjectsTable.deleteWhere {
                (SubjectsTable.id eq subjectId) and
                        (SubjectsTable.userId eq userId)
            }

            deletedRows > 0
        }
    }

    private fun getOrCreateUser(firebaseUid: String): Int {
        val existingUser = UsersTable
            .selectAll()
            .where { UsersTable.firebaseUid eq firebaseUid }
            .map { row -> row[UsersTable.id] }
            .singleOrNull()

        if (existingUser != null) {
            return existingUser
        }

        return UsersTable.insert { row ->
            row[UsersTable.firebaseUid] = firebaseUid
            row[UsersTable.email] = "unknown@example.com"
            row[UsersTable.name] = "Пользователь"
        } get UsersTable.id
    }
}