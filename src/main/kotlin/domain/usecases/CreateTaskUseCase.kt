package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task {
        return taskRepository.createTask(
            title = title,
            description = description,
            subject = subject,
            deadline = deadline,
            priority = priority,
            type = type
        )
    }
}