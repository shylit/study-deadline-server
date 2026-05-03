package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.SubjectsTable
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import org.jetbrains.exposed.v1.jdbc.insert
import ru.mirea.shylit.studydeadline.data.tables.UsersTable
import org.jetbrains.exposed.v1.core.eq

class PostgresSubjectRepository : SubjectRepository {

    override fun getAllSubjects(): List<Subject> {
        return transaction {
            SubjectsTable.selectAll().map { row ->
                Subject(
                    id = row[SubjectsTable.id],
                    name = row[SubjectsTable.name],
                    description = row[SubjectsTable.description]
                )
            }
        }
    }

    override fun getSubjectById(subjectId: Int): Subject? {
        return transaction {
            SubjectsTable
                .selectAll()
                .where { SubjectsTable.id eq subjectId }
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

    override fun searchSubjects(query: String?): List<Subject> {
        return transaction {

            if (query.isNullOrBlank()) {
                return@transaction getAllSubjects()
            }

            getAllSubjects().filter { subject ->
                subject.name.contains(query, ignoreCase = true) ||
                        subject.description.contains(query, ignoreCase = true)
            }
        }
    }

    override fun createSubject(
        name: String,
        description: String
    ): Subject {
        return transaction {
            val userId = getOrCreateDemoUser()

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
        subjectId: Int,
        name: String,
        description: String
    ): Subject? = TODO()

    override fun deleteSubject(subjectId: Int): Boolean = TODO()

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