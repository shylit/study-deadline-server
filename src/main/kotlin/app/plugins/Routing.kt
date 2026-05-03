package ru.mirea.shylit.studydeadline.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.mirea.shylit.studydeadline.data.repositories.InMemorySubjectRepository
import ru.mirea.shylit.studydeadline.data.repositories.InMemoryTaskRepository
import ru.mirea.shylit.studydeadline.data.repositories.InMemoryUserRepository
import ru.mirea.shylit.studydeadline.data.repositories.PostgresTaskRepository
import ru.mirea.shylit.studydeadline.domain.usecases.CreateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.CreateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.SearchSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.DeleteTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksForTodayUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksForWeekUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTasksBySubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.UpdateTaskStatusUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.ValidateTaskUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.ValidateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetTaskByIdUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetSubjectByIdUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.GetCurrentUserUseCase
import ru.mirea.shylit.studydeadline.presentation.routes.healthRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.subjectRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.taskRoutes
import ru.mirea.shylit.studydeadline.presentation.routes.userRoutes

fun Application.configureRouting() {
    val taskRepository = PostgresTaskRepository()
    val subjectRepository = InMemorySubjectRepository()

    val getTasksUseCase = GetTasksUseCase(taskRepository)
    val createTaskUseCase = CreateTaskUseCase(taskRepository)
    val searchTasksUseCase = SearchTasksUseCase(taskRepository)
    val deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    val updateTaskUseCase = UpdateTaskUseCase(taskRepository)
    val getTasksBySubjectUseCase = GetTasksBySubjectUseCase(taskRepository)
    val getTasksForTodayUseCase = GetTasksForTodayUseCase(taskRepository)
    val getTasksForWeekUseCase = GetTasksForWeekUseCase(taskRepository)
    val getTaskByIdUseCase = GetTaskByIdUseCase(taskRepository)

    val validateTaskUseCase = ValidateTaskUseCase()
    val validateSubjectUseCase = ValidateSubjectUseCase()

    val getSubjectsUseCase = GetSubjectsUseCase(subjectRepository)
    val createSubjectUseCase = CreateSubjectUseCase(subjectRepository)
    val deleteSubjectUseCase = DeleteSubjectUseCase(subjectRepository)
    val searchSubjectsUseCase = SearchSubjectsUseCase(subjectRepository)
    val updateSubjectUseCase = UpdateSubjectUseCase(subjectRepository)
    val getSubjectByIdUseCase = GetSubjectByIdUseCase(subjectRepository)

    val updateTaskStatusUseCase = UpdateTaskStatusUseCase(taskRepository)

    val userRepository = InMemoryUserRepository()
    val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)

    routing {
        healthRoutes()
        taskRoutes(
            getTasksUseCase = getTasksUseCase,
            createTaskUseCase = createTaskUseCase,
            searchTasksUseCase = searchTasksUseCase,
            deleteTaskUseCase = deleteTaskUseCase,
            updateTaskUseCase = updateTaskUseCase,
            getTasksForTodayUseCase = getTasksForTodayUseCase,
            getTasksForWeekUseCase = getTasksForWeekUseCase,
            getTasksBySubjectUseCase = getTasksBySubjectUseCase,
            updateTaskStatusUseCase = updateTaskStatusUseCase,
            validateTaskUseCase = validateTaskUseCase,
            getTaskByIdUseCase = getTaskByIdUseCase
        )
        subjectRoutes(
            getSubjectsUseCase = getSubjectsUseCase,
            createSubjectUseCase = createSubjectUseCase,
            searchSubjectsUseCase = searchSubjectsUseCase,
            deleteSubjectUseCase = deleteSubjectUseCase,
            updateSubjectUseCase = updateSubjectUseCase,
            validateSubjectUseCase = validateSubjectUseCase,
            getSubjectByIdUseCase = getSubjectByIdUseCase
        )
        userRoutes(
            getCurrentUserUseCase = getCurrentUserUseCase
        )
    }
}