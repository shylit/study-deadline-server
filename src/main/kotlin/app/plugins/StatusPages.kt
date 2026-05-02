package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import ru.mirea.shylit.studydeadline.presentation.dto.ErrorResponse

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled error", cause)

            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse("Внутренняя ошибка сервера")
            )
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ErrorResponse("Маршрут не найден")
            )
        }
    }
}