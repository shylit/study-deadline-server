package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority

interface TaskRepository {
    fun getAllTasks(): List<Task>

    fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority
    ): Task
}