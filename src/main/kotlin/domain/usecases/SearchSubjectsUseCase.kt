package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class SearchSubjectsUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(query: String?): List<Subject> {
        return subjectRepository.searchSubjects(query)
    }
}