package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class CreateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(
        name: String,
        description: String
    ): Subject {
        return subjectRepository.createSubject(
            name = name,
            description = description
        )
    }
}