package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.SubjectResponse

fun Route.subjectRoutes(
    getSubjectsUseCase: GetSubjectsUseCase
) {
    route("/api/subjects") {
        get {
            val subjects = getSubjectsUseCase()
            call.respond(subjects.map { it.toResponse() })
        }
    }
}

private fun Subject.toResponse(): SubjectResponse {
    return SubjectResponse(
        id = id,
        name = name,
        description = description
    )
}