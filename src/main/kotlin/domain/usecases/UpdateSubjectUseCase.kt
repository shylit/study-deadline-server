package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class UpdateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(
        firebaseUid: String,
        subjectId: Int,
        name: String,
        description: String
    ): Subject? {
        return subjectRepository.updateSubject(
            firebaseUid = firebaseUid,
            subjectId = subjectId,
            name = name,
            description = description
        )
    }
}