package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class DeleteSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(
        firebaseUid: String,
        subjectId: Int
    ): Boolean {
        return subjectRepository.deleteSubject(
            firebaseUid = firebaseUid,
            subjectId = subjectId
        )
    }
}