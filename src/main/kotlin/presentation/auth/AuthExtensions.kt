package ru.mirea.shylit.studydeadline.presentation.auth

import io.ktor.server.application.ApplicationCall
import ru.mirea.shylit.studydeadline.domain.exceptions.UnauthorizedException

fun ApplicationCall.getCurrentUserSession(): UserSession {
    val authHeader = request.headers["Authorization"]
        ?: throw UnauthorizedException()

    val token = authHeader
        .removePrefix("Bearer ")
        .trim()

    if (token.isBlank() || token == authHeader) {
        throw UnauthorizedException()
    }

    return FirebaseTokenVerifier.verify(token)
        ?: throw UnauthorizedException()
}