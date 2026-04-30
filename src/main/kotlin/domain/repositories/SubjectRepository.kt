package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Subject

interface SubjectRepository {
    fun getAllSubjects(): List<Subject>

    fun searchSubjects(query: String?): List<Subject>

    fun createSubject(
        name: String,
        description: String
    ): Subject
}