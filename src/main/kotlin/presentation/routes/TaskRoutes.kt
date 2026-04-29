package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.usecases.CreateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.CreateTaskRequest
import ru.mirea.shylit.studydeadline.presentation.dto.TaskResponse

fun Route.taskRoutes(
    getTasksUseCase: GetTasksUseCase,
    createTaskUseCase: CreateTaskUseCase
) {
    route("/api/tasks") {
        get {
            val tasks = getTasksUseCase()
            call.respond(tasks.map { it.toResponse() })
        }

        post {
            val request = call.receive<CreateTaskRequest>()

            val task = createTaskUseCase(
                title = request.title,
                description = request.description,
                subject = request.subject,
                deadline = request.deadline
            )

            call.respond(
                status = HttpStatusCode.Created,
                message = task.toResponse()
            )
        }
    }
}

private fun Task.toResponse(): TaskResponse {
    return TaskResponse(
        id = id,
        title = title,
        description = description,
        subject = subject,
        deadline = deadline,
        isCompleted = isCompleted
    )
}