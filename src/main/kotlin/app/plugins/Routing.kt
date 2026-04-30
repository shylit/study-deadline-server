package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.data.repositories.InMemorySubjectRepository
import ru.mirea.shylit.studydeadline.data.repositories.InMemoryTaskRepository
import ru.mirea.shylit.studydeadline.domain.usecases.CreateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.CreateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchSubjectsUseCase
import ru.mirea.shylit.studydeadline.presentation.routes.healthRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.subjectRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.taskRoutes

fun Application.configureRouting() {
    val taskRepository = InMemoryTaskRepository()
    val subjectRepository = InMemorySubjectRepository()

    val getTasksUseCase = GetTasksUseCase(taskRepository)
    val createTaskUseCase = CreateTaskUseCase(taskRepository)
    val searchTasksUseCase = SearchTasksUseCase(taskRepository)

    val getSubjectsUseCase = GetSubjectsUseCase(subjectRepository)
    val createSubjectUseCase = CreateSubjectUseCase(subjectRepository)
    val searchSubjectsUseCase = SearchSubjectsUseCase(subjectRepository)

    routing {
        healthRoutes()
        taskRoutes(

            getTasksUseCase = getTasksUseCase,

            createTaskUseCase = createTaskUseCase,

            searchTasksUseCase = searchTasksUseCase

        )
        subjectRoutes(
            getSubjectsUseCase = getSubjectsUseCase,
            createSubjectUseCase = createSubjectUseCase,
            searchSubjectsUseCase = searchSubjectsUseCase
        )
    }
}