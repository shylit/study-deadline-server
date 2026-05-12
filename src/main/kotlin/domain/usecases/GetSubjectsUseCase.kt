package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Subject

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class GetSubjectsUseCase(

    private val subjectRepository: SubjectRepository

) {

    operator fun invoke(firebaseUid: String): List<Subject> {

        return subjectRepository.getAllSubjects(firebaseUid)

    }

}