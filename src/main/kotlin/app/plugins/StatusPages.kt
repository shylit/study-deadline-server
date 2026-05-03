package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import ru.mirea.shylit.studydeadline.presentation.dto.ErrorResponse

fun Application.configureStatusPages() {

    install(StatusPages) {

        exception<Throwable> { call, cause ->

            cause.printStackTrace()

            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    message = "Внутренняя ошибка сервера"
                )
            )
        }

        status(HttpStatusCode.NotFound) { call, status ->

            call.respond(
                status = status,
                message = ErrorResponse(
                    message = "Маршрут не найден"
                )
            )
        }
    }
}