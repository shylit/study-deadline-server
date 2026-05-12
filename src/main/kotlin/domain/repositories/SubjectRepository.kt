package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Subject

interface SubjectRepository {

    fun getAllSubjects(firebaseUid: String): List<Subject>

    fun getSubjectById(
        firebaseUid: String,
        subjectId: Int
    ): Subject?

    fun searchSubjects(
        firebaseUid: String,
        query: String?
    ): List<Subject>

    fun createSubject(
        firebaseUid: String,
        name: String,
        description: String
    ): Subject

    fun updateSubject(
        firebaseUid: String,
        subjectId: Int,
        name: String,
        description: String
    ): Subject?

    fun deleteSubject(
        firebaseUid: String,
        subjectId: Int
    ): Boolean
}