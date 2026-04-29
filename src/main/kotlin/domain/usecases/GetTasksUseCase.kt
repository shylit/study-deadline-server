package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): List<Task> {
        return taskRepository.getAllTasks()
    }
}