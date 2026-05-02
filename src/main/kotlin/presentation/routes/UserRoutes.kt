package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.User
import ru.mirea.shylit.studydeadline.domain.usecases.GetCurrentUserUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.UserResponse

fun Route.userRoutes(
    getCurrentUserUseCase: GetCurrentUserUseCase
) {
    route("/api/users") {
        get("/me") {
            val user = getCurrentUserUseCase()
            call.respond(user.toResponse())
        }
    }
}

private fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id,
        firebaseUid = firebaseUid,
        email = email,
        name = name
    )
}