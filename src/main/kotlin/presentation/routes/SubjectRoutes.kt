package ru.mirea.shylit.studydeadline.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.usecases.CreateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectByIdUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.ValidateSubjectUseCase
import ru.mirea.shylit.studydeadline.presentation.auth.getCurrentUserSession
import ru.mirea.shylit.studydeadline.presentation.dto.CreateSubjectRequest
import ru.mirea.shylit.studydeadline.presentation.dto.ErrorResponse
import ru.mirea.shylit.studydeadline.presentation.dto.SubjectResponse
import ru.mirea.shylit.studydeadline.presentation.dto.UpdateSubjectRequest

fun Route.subjectRoutes(
    getSubjectsUseCase: GetSubjectsUseCase,
    createSubjectUseCase: CreateSubjectUseCase,
    searchSubjectsUseCase: SearchSubjectsUseCase,
    deleteSubjectUseCase: DeleteSubjectUseCase,
    updateSubjectUseCase: UpdateSubjectUseCase,
    validateSubjectUseCase: ValidateSubjectUseCase,
    getSubjectByIdUseCase: GetSubjectByIdUseCase
) {
    route("/api/subjects") {

        get {
            val userSession = call.getCurrentUserSession()
            val query = call.request.queryParameters["query"]

            val subjects = if (query.isNullOrBlank()) {
                getSubjectsUseCase(userSession.firebaseUid)
            } else {
                searchSubjectsUseCase(
                    firebaseUid = userSession.firebaseUid,
                    query = query
                )
            }

            call.respond(subjects.map { it.toResponse() })
        }

        get("/{id}") {
            val userSession = call.getCurrentUserSession()
            val subjectId = call.parameters["id"]?.toIntOrNull()

            if (subjectId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректный id предмета")
                )
                return@get
            }

            val subject = getSubjectByIdUseCase(
                firebaseUid = userSession.firebaseUid,
                subjectId = subjectId
            )

            if (subject == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse("Предмет не найден")
                )
                return@get
            }

            call.respond(subject.toResponse())
        }

        post {
            val userSession = call.getCurrentUserSession()
            val request = call.receive<CreateSubjectRequest>()

            val validationError = validateSubjectUseCase(
                name = request.name,
                description = request.description
            )

            if (validationError != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(validationError)
                )
                return@post
            }

            val subject = createSubjectUseCase(
                firebaseUid = userSession.firebaseUid,
                name = request.name,
                description = request.description
            )

            call.respond(
                status = HttpStatusCode.Created,
                message = subject.toResponse()
            )
        }

        put("/{id}") {
            val userSession = call.getCurrentUserSession()
            val subjectId = call.parameters["id"]?.toIntOrNull()

            if (subjectId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректный id предмета")
                )
                return@put
            }

            val request = call.receive<UpdateSubjectRequest>()

            val validationError = validateSubjectUseCase(
                name = request.name,
                description = request.description
            )

            if (validationError != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(validationError)
                )
                return@put
            }

            val updatedSubject = updateSubjectUseCase(
                firebaseUid = userSession.firebaseUid,
                subjectId = subjectId,
                name = request.name,
                description = request.description
            )

            if (updatedSubject == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse("Предмет не найден")
                )
                return@put
            }

            call.respond(updatedSubject.toResponse())
        }

        delete("/{id}") {
            val userSession = call.getCurrentUserSession()
            val subjectId = call.parameters["id"]?.toIntOrNull()

            if (subjectId == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("Некорректный id предмета")
                )
                return@delete
            }

            val isDeleted = deleteSubjectUseCase(
                firebaseUid = userSession.firebaseUid,
                subjectId = subjectId
            )

            if (!isDeleted) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse("Предмет не найден")
                )
                return@delete
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = mapOf("message" to "Предмет удален")
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