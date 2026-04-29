package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.data.repositories.InMemoryTaskRepository
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.presentation.routes.healthRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.taskRoutes

fun Application.configureRouting() {
    val taskRepository = InMemoryTaskRepository()
    val getTasksUseCase = GetTasksUseCase(taskRepository)

    routing {
        healthRoutes()
        taskRoutes(getTasksUseCase)
    }
}