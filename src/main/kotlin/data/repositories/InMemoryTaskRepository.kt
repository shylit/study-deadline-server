package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class InMemoryTaskRepository : TaskRepository {

    private val tasks = listOf(
        Task(
            id = 1,
            title = "Подготовить главу 1 курсовой",
            description = "Описать предметную область и аналоги приложения",
            subject = "Разработка клиент-серверных мобильных приложений",
            deadline = "2026-05-05",
            isCompleted = false
        ),
        Task(
            id = 2,
            title = "Сделать лабораторную работу по Kotlin",
            description = "Реализовать экран со списком элементов",
            subject = "Мобильная разработка",
            deadline = "2026-05-10",
            isCompleted = false
        ),
        Task(
            id = 3,
            title = "Подготовиться к тесту",
            description = "Повторить темы по REST API и базам данных",
            subject = "Клиент-серверные приложения",
            deadline = "2026-05-15",
            isCompleted = true
        )
    )

    override fun getAllTasks(): List<Task> = tasks
}