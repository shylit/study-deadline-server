package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTasksForWeekUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        firebaseUid: String,
        startDate: String,
        endDate: String
    ): List<Task> {
        return taskRepository.getTasksForWeek(
            firebaseUid = firebaseUid,
            startDate = startDate,
            endDate = endDate
        )
    }
}