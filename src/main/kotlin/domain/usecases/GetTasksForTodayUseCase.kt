package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTasksForTodayUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(today: String): List<Task> {
        return taskRepository.getTasksForToday(today)
    }
}