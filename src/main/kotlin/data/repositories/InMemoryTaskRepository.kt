package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
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
            priority = TaskPriority.HIGH
        ),
        Task(
            id = 2,
            title = "Сделать лабораторную работу по Kotlin",
            description = "Реализовать экран со списком элементов",
            subject = "Мобильная разработка",
            deadline = "2026-05-10",
            status = TaskStatus.PLANNED,
            priority = TaskPriority.MEDIUM
        ),
        Task(
            id = 3,
            title = "Подготовиться к тесту",
            description = "Повторить темы по REST API и базам данных",
            subject = "Клиент-серверные приложения",
            deadline = "2026-05-15",
            status = TaskStatus.COMPLETED,
            priority = TaskPriority.LOW
        )
    )

    override fun getAllTasks(): List<Task> = tasks

    override fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority
    ): Task {
        val task = Task(
            id = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1,
            title = title,
            description = description,
            subject = subject,
            deadline = deadline,
            status = TaskStatus.PLANNED,
            priority = priority
        )

        tasks.add(task)

        return task
    }
}