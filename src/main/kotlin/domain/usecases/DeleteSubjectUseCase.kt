package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class DeleteSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(subjectId: Int): Boolean {
        return subjectRepository.deleteSubject(subjectId)
    }
}