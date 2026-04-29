package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.healthRoutes() {
    get("/") {
        call.respondText("Study Deadline Server запущен")
    }

    get("/health") {
        call.respondText("OK")
    }
}