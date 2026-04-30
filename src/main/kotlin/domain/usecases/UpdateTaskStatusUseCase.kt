package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class UpdateTaskStatusUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        taskId: Int,
        status: TaskStatus
    ): Task? {
        return taskRepository.updateTaskStatus(
            taskId = taskId,
            status = status
        )
    }
}