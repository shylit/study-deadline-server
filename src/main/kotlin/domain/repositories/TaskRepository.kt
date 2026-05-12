package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType

interface TaskRepository {

    fun getAllTasks(firebaseUid: String): List<Task>

    fun getTaskById(
        firebaseUid: String,
        taskId: Int
    ): Task?

    fun getTasksBySubject(
        firebaseUid: String,
        subject: String
    ): List<Task>

    fun getTasksForToday(
        firebaseUid: String,
        today: String
    ): List<Task>

    fun getTasksForWeek(
        firebaseUid: String,
        startDate: String,
        endDate: String
    ): List<Task>

    fun searchTasks(
        firebaseUid: String,
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task>

    fun createTask(
        firebaseUid: String,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task

    fun updateTask(
        firebaseUid: String,
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
        firebaseUid: String,
        taskId: Int,
        status: TaskStatus
    ): Task?

    fun deleteTask(
        firebaseUid: String,
        taskId: Int
    ): Boolean
}