package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class SearchTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        firebaseUid: String,
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task> {
        return taskRepository.searchTasks(
            firebaseUid = firebaseUid,
            query = query,
            status = status,
            priority = priority
        )
    }
}