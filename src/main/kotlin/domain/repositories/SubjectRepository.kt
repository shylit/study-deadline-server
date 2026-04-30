package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Subject

interface SubjectRepository {
    fun getAllSubjects(): List<Subject>
}