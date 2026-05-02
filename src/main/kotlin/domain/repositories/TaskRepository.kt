package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType

interface TaskRepository {
    fun getAllTasks(): List<Task>

    fun getTasksBySubject(subject: String): List<Task>

    fun searchTasks(
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task>

    fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task

    fun deleteTask(taskId: Int): Boolean

    fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        status: TaskStatus,
        priority: TaskPriority,
        type: TaskType
    ): Task?

    fun updateTaskStatus(
        taskId: Int,
        status: TaskStatus
    ): Task?

    fun getTasksForToday(today: String): List<Task>

    fun getTasksForWeek(
        startDate: String,
        endDate: String
    ): List<Task>
}