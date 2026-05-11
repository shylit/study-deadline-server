package ru.mirea.shylit.studydeadline

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.mirea.shylit.studydeadline.app.plugins.configureRouting
import ru.mirea.shylit.studydeadline.app.plugins.configureSerialization
import ru.mirea.shylit.studydeadline.app.plugins.configureCors
import ru.mirea.shylit.studydeadline.app.plugins.configureStatusPages
import ru.mirea.shylit.studydeadline.data.database.DatabaseFactory
import ru.mirea.shylit.studydeadline.app.firebase.FirebaseFactory

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init()

    FirebaseFactory.init()

    configureCors()
    configureSerialization()
    configureStatusPages()
    configureRouting()
}