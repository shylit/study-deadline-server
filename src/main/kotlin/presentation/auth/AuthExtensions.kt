package ru.mirea.shylit.studydeadline.presentation.auth

import io.ktor.server.application.ApplicationCall

fun ApplicationCall.getCurrentUserSession(): UserSession {

    val authHeader =
        request.headers["Authorization"]
            ?: return UserSession(
                firebaseUid = "demo-firebase-uid"
            )

    val token =
        authHeader.removePrefix("Bearer ").trim()

    return FirebaseTokenVerifier.verify(token)
        ?: UserSession(
            firebaseUid = "demo-firebase-uid"
        )
}