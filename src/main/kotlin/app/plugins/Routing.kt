package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.presentation.routes.healthRoutes

fun Application.configureRouting() {
    routing {
        healthRoutes()
    }
}