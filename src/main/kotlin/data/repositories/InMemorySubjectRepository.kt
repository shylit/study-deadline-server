package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class InMemorySubjectRepository : SubjectRepository {

    private val subjects = mutableListOf(
        Subject(
            id = 1,
            name = "Разработка клиент-серверных мобильных приложений",
            description = "Дисциплина по разработке мобильных приложений с серверной частью"
        ),
        Subject(
            id = 2,
            name = "Мобильная разработка",
            description = "Дисциплина по разработке Android-приложений"
        ),
        Subject(
            id = 3,
            name = "Проектирование баз данных",
            description = "Дисциплина по проектированию логических и физических моделей данных"
        )
    )

    override fun getAllSubjects(): List<Subject> = subjects

    override fun searchSubjects(query: String?): List<Subject> {
        return subjects.filter { subject ->
            query.isNullOrBlank() ||
                    subject.name.contains(query, ignoreCase = true) ||
                    subject.description.contains(query, ignoreCase = true)
        }
    }

    override fun createSubject(
        name: String,
        description: String
    ): Subject {
        val subject = Subject(
            id = subjects.maxOfOrNull { it.id }?.plus(1) ?: 1,
            name = name,
            description = description
        )

        subjects.add(subject)

        return subject
    }

    override fun deleteSubject(subjectId: Int): Boolean {
        return subjects.removeIf { it.id == subjectId }
    }

    override fun updateSubject(
        subjectId: Int,
        name: String,
        description: String
    ): Subject? {
        val subjectIndex = subjects.indexOfFirst { it.id == subjectId }

        if (subjectIndex == -1) {
            return null
        }

        val updatedSubject = Subject(
            id = subjectId,
            name = name,
            description = description
        )

        subjects[subjectIndex] = updatedSubject

        return updatedSubject
    }
}