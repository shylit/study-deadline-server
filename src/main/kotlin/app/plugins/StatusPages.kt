package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import ru.mirea.shylit.studydeadline.presentation.dto.ErrorResponse
import ru.mirea.shylit.studydeadline.domain.exceptions.UnauthorizedException

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

        exception<UnauthorizedException> { call, cause ->
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ErrorResponse(
                    message = cause.message ?: "Пользователь не авторизован"
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