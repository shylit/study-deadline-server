package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTasksBySubjectUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(subject: String): List<Task> {
        return taskRepository.getTasksBySubject(subject)
    }
}