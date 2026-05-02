package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        taskId: Int,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        status: TaskStatus,
        priority: TaskPriority,
        type: TaskType
    ): Task? {
        return taskRepository.updateTask(
            taskId = taskId,
            title = title,
            description = description,
            subject = subject,
            deadline = deadline,
            status = status,
            priority = priority,
            type = type
        )
    }
}