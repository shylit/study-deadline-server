package ru.mirea.shylit.studydeadline.presentation.auth

import io.ktor.server.application.ApplicationCall

fun ApplicationCall.getCurrentUserSession(): UserSession {

    return UserSession(
        firebaseUid = "demo-firebase-uid"
    )
}