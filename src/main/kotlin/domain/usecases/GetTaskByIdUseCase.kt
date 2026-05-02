package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(taskId: Int): Task? {
        return taskRepository.getTaskById(taskId)
    }
}