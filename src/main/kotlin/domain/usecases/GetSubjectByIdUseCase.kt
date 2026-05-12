package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class GetSubjectByIdUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(
        firebaseUid: String,
        subjectId: Int
    ): Subject? {
        return subjectRepository.getSubjectById(
            firebaseUid = firebaseUid,
            subjectId = subjectId
        )
    }
}