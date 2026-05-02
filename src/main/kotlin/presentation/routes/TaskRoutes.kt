package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate
import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.usecases.CreateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksBySubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskStatusUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksForTodayUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksForWeekUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.ValidateTaskUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.CreateTaskRequest
import ru.mirea.shylit.studydeadline.presentation.dto.UpdateTaskRequest
import ru.mirea.shylit.studydeadline.presentation.dto.TaskResponse
import ru.mirea.shylit.studydeadline.presentation.dto.UpdateTaskStatusRequest
import ru.mirea.shylit.studydeadline.presentation.dto.ErrorResponse

fun Route.taskRoutes(
    getTasksUseCase: GetTasksUseCase,
    createTaskUseCase: CreateTaskUseCase,
    searchTasksUseCase: SearchTasksUseCase,
    deleteTaskUseCase: DeleteTaskUseCase,
    updateTaskUseCase: UpdateTaskUseCase,
    getTasksForTodayUseCase: GetTasksForTodayUseCase,
    getTasksForWeekUseCase: GetTasksForWeekUseCase,
    getTasksBySubjectUseCase: GetTasksBySubjectUseCase,
    updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    validateTaskUseCase: ValidateTaskUseCase
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
                    message = ErrorResponse("Не указан параметр subject")
                )
                return@get
            }

            val tasks = getTasksBySubjectUseCase(subject)
            call.respond(tasks.map { it.toResponse() })
        }

        get("/today") {
            val today = LocalDate.now().toString()
            val tasks = getTasksForTodayUseCase(today)

            call.respond(tasks.map { it.toResponse() })
        }

        get("/week") {
            val startDate = LocalDate.now()
            val endDate = startDate.plusDays(7)

            val tasks = getTasksForWeekUseCase(
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )

            call.respond(tasks.map { it.toResponse() })
        }

        post {
            val request = call.receive<CreateTaskRequest>()

            val validationError = validateTaskUseCase(

                title = request.title,

                description = request.description,

                subject = request.subject,

                deadline = request.deadline

            )

            if (validationError != null) {

                call.respond(

                    status = HttpStatusCode.BadRequest,

                    message = ErrorResponse(validationError)

                )

                return@post

            }

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

        put("/{id}") {
            val taskId = call.parameters["id"]?.toIntOrNull()

            if (taskId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректный id задания")
                )
                return@put
            }

            val request = call.receive<UpdateTaskRequest>()

            val validationError = validateTaskUseCase(

                title = request.title,

                description = request.description,

                subject = request.subject,

                deadline = request.deadline

            )

            if (validationError != null) {

                call.respond(

                    status = HttpStatusCode.BadRequest,

                    message = ErrorResponse(validationError)

                )

                return@put

            }

            val status = runCatching {
                TaskStatus.valueOf(request.status.uppercase())
            }.getOrNull()

            val priority = runCatching {
                TaskPriority.valueOf(request.priority.uppercase())
            }.getOrNull()

            val type = runCatching {
                TaskType.valueOf(request.type.uppercase())
            }.getOrNull()

            if (status == null || priority == null || type == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректные значения статуса, приоритета или типа задания")
                )
                return@put
            }

            val updatedTask = updateTaskUseCase(
                taskId = taskId,
                title = request.title,
                description = request.description,
                subject = request.subject,
                deadline = request.deadline,
                status = status,
                priority = priority,
                type = type
            )

            if (updatedTask == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse("Задание не найдено")
                )
                return@put
            }

            call.respond(updatedTask.toResponse())
        }

        delete("/{id}") {
            val taskId = call.parameters["id"]?.toIntOrNull()

            if (taskId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректный id задания")
                )
                return@delete
            }

            val isDeleted = deleteTaskUseCase(taskId)

            if (!isDeleted) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse("Задание не найдено")
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
                    message = ErrorResponse("Некорректный id задания")
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
                    message = ErrorResponse("Некорректный статус задания")
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
                    message = ErrorResponse("Задание не найдено")
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