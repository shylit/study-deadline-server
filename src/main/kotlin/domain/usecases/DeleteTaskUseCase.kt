package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(taskId: Int): Boolean {
        return taskRepository.deleteTask(taskId)
    }
}