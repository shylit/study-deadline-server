package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.SubjectsTable
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

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

    override fun getSubjectById(subjectId: Int): Subject? = TODO()

    override fun searchSubjects(query: String?): List<Subject> = TODO()

    override fun createSubject(
        name: String,
        description: String
    ): Subject = TODO()

    override fun updateSubject(
        subjectId: Int,
        name: String,
        description: String
    ): Subject? = TODO()

    override fun deleteSubject(subjectId: Int): Boolean = TODO()
}