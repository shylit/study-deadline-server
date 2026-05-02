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
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksBySubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskStatusUseCase
import ru.mirea.shylit.studydeadline.presentation.routes.healthRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.subjectRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.taskRoutes

fun Application.configureRouting() {
    val taskRepository = InMemoryTaskRepository()
    val subjectRepository = InMemorySubjectRepository()

    val getTasksUseCase = GetTasksUseCase(taskRepository)
    val createTaskUseCase = CreateTaskUseCase(taskRepository)
    val searchTasksUseCase = SearchTasksUseCase(taskRepository)
    val deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    val getTasksBySubjectUseCase = GetTasksBySubjectUseCase(taskRepository)

    val getSubjectsUseCase = GetSubjectsUseCase(subjectRepository)
    val createSubjectUseCase = CreateSubjectUseCase(subjectRepository)
    val searchSubjectsUseCase = SearchSubjectsUseCase(subjectRepository)

    val updateTaskStatusUseCase = UpdateTaskStatusUseCase(taskRepository)

    routing {
        healthRoutes()
        taskRoutes(
            getTasksUseCase = getTasksUseCase,
            createTaskUseCase = createTaskUseCase,
            searchTasksUseCase = searchTasksUseCase,
            deleteTaskUseCase = deleteTaskUseCase,
            getTasksBySubjectUseCase = getTasksBySubjectUseCase,
            updateTaskStatusUseCase = updateTaskStatusUseCase
        )
        subjectRoutes(
            getSubjectsUseCase = getSubjectsUseCase,
            createSubjectUseCase = createSubjectUseCase,
            searchSubjectsUseCase = searchSubjectsUseCase
        )
    }
}