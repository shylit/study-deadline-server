package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.TaskResponse

fun Route.taskRoutes(
    getTasksUseCase: GetTasksUseCase
) {
    route("/api/tasks") {
        get {
            val tasks = getTasksUseCase()

            val response = tasks.map { task ->
                TaskResponse(
                    id = task.id,
                    title = task.title,
                    description = task.description,
                    subject = task.subject,
                    deadline = task.deadline,
                    isCompleted = task.isCompleted
                )
            }

            call.respond(response)
        }
    }
}