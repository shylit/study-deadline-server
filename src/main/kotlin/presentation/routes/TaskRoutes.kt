package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.usecases.CreateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksBySubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskStatusUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.CreateTaskRequest
import ru.mirea.shylit.studydeadline.presentation.dto.TaskResponse
import ru.mirea.shylit.studydeadline.presentation.dto.UpdateTaskStatusRequest

fun Route.taskRoutes(
    getTasksUseCase: GetTasksUseCase,
    createTaskUseCase: CreateTaskUseCase,
    searchTasksUseCase: SearchTasksUseCase,
    deleteTaskUseCase: DeleteTaskUseCase,
    getTasksBySubjectUseCase: GetTasksBySubjectUseCase,
    updateTaskStatusUseCase: UpdateTaskStatusUseCase
) {
    route("/api/tasks") {
        get {
            val query = call.request.queryParameters["query"]

            val status = call.request.queryParameters["status"]?.let { value ->
                runCatching {
                    TaskStatus.valueOf(value.uppercase())
                }.getOrNull()
            }

            val priority = call.request.queryParameters["priority"]?.let { value ->
                runCatching {
                    TaskPriority.valueOf(value.uppercase())
                }.getOrNull()
            }

            val tasks = if (query.isNullOrBlank() && status == null && priority == null) {
                getTasksUseCase()
            } else {
                searchTasksUseCase(
                    query = query,
                    status = status,
                    priority = priority
                )
            }

            call.respond(tasks.map { it.toResponse() })
        }

        get("/by-subject") {
            val subject = call.request.queryParameters["subject"]

            if (subject.isNullOrBlank()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Не указан параметр subject")
                )
                return@get
            }

            val tasks = getTasksBySubjectUseCase(subject)
            call.respond(tasks.map { it.toResponse() })
        }

        post {
            val request = call.receive<CreateTaskRequest>()

            val priority = runCatching {
                TaskPriority.valueOf(request.priority.uppercase())
            }.getOrDefault(TaskPriority.MEDIUM)

            val type = runCatching {
                TaskType.valueOf(request.type.uppercase())
            }.getOrDefault(TaskType.OTHER)

            val task = createTaskUseCase(
                title = request.title,
                description = request.description,
                subject = request.subject,
                deadline = request.deadline,
                priority = priority,
                type = type
            )

            call.respond(
                status = HttpStatusCode.Created,
                message = task.toResponse()
            )
        }

        delete("/{id}") {
            val taskId = call.parameters["id"]?.toIntOrNull()

            if (taskId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Некорректный id задания")
                )
                return@delete
            }

            val isDeleted = deleteTaskUseCase(taskId)

            if (!isDeleted) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = mapOf("error" to "Задание не найдено")
                )
                return@delete
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = mapOf("message" to "Задание удалено")
            )
        }

        patch("/{id}/status") {

            val taskId = call.parameters["id"]?.toIntOrNull()

            if (taskId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Некорректный id задания")
                )
                return@patch
            }

            val request = call.receive<UpdateTaskStatusRequest>()

            val status = runCatching {
                TaskStatus.valueOf(request.status.uppercase())
            }.getOrNull()

            if (status == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Некорректный статус задания")
                )
                return@patch
            }

            val updatedTask = updateTaskStatusUseCase(
                taskId = taskId,
                status = status
            )

            if (updatedTask == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = mapOf("error" to "Задание не найдено")
                )
                return@patch
            }

            call.respond(updatedTask.toResponse())
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
        status = status.name,
        priority = priority.name,
        type = type.name
    )
}