package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.usecases.CreateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.presentation.dto.CreateSubjectRequest
import ru.mirea.shylit.studydeadline.presentation.dto.SubjectResponse

fun Route.subjectRoutes(
    getSubjectsUseCase: GetSubjectsUseCase,
    createSubjectUseCase: CreateSubjectUseCase
) {
    route("/api/subjects") {
        get {
            val subjects = getSubjectsUseCase()
            call.respond(subjects.map { it.toResponse() })
        }

        post {
            val request = call.receive<CreateSubjectRequest>()

            val subject = createSubjectUseCase(
                name = request.name,
                description = request.description
            )

            call.respond(
                status = HttpStatusCode.Created,
                message = subject.toResponse()
            )
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