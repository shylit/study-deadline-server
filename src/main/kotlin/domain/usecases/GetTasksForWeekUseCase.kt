package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTasksForWeekUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        startDate: String,
        endDate: String
    ): List<Task> {
        return taskRepository.getTasksForWeek(
            startDate = startDate,
            endDate = endDate
        )
    }
}