package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class InMemoryTaskRepository : TaskRepository {

    private val tasks = mutableListOf(
        Task(
            id = 1,
            title = "Подготовить главу 1 курсовой",
            description = "Описать предметную область и аналоги приложения",
            subject = "Разработка клиент-серверных мобильных приложений",
            deadline = "2026-05-05",
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.HIGH,
            type = TaskType.COURSE_WORK
        ),
        Task(
            id = 2,
            title = "Сделать лабораторную работу по Kotlin",
            description = "Реализовать экран со списком элементов",
            subject = "Мобильная разработка",
            deadline = "2026-05-10",
            status = TaskStatus.PLANNED,
            priority = TaskPriority.MEDIUM,
            type = TaskType.LAB_WORK
        ),
        Task(
            id = 3,
            title = "Подготовиться к тесту",
            description = "Повторить темы по REST API и базам данных",
            subject = "Клиент-серверные приложения",
            deadline = "2026-05-15",
            status = TaskStatus.COMPLETED,
            priority = TaskPriority.LOW,
            type = TaskType.TEST
        )
    )

    override fun getAllTasks(): List<Task> = tasks

    override fun getTasksBySubject(subject: String): List<Task> {
        return tasks.filter { task ->
            task.subject.equals(subject, ignoreCase = true)
        }
    }

    override fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task {
        val task = Task(
            id = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1,
            title = title,
            description = description,
            subject = subject,
            deadline = deadline,
            status = TaskStatus.PLANNED,
            priority = priority,
            type = type
        )

        tasks.add(task)

        return task
    }

    override fun searchTasks(
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task> {
        return tasks.filter { task ->
            val matchesQuery = query.isNullOrBlank() ||
                    task.title.contains(query, ignoreCase = true) ||
                    task.description.contains(query, ignoreCase = true) ||
                    task.subject.contains(query, ignoreCase = true)

            val matchesStatus = status == null || task.status == status
            val matchesPriority = priority == null || task.priority == priority

            matchesQuery && matchesStatus && matchesPriority
        }
    }

    override fun deleteTask(taskId: Int): Boolean {
        return tasks.removeIf { it.id == taskId }
    }

    override fun updateTaskStatus(
        taskId: Int,
        status: TaskStatus
    ): Task? {

        val taskIndex = tasks.indexOfFirst { it.id == taskId }

        if (taskIndex == -1) {
            return null
        }

        val updatedTask = tasks[taskIndex].copy(
            status = status
        )

        tasks[taskIndex] = updatedTask

        return updatedTask
    }
}