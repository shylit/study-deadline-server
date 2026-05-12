package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        firebaseUid: String,
        taskId: Int
    ): Task? {
        return taskRepository.getTaskById(
            firebaseUid = firebaseUid,
            taskId = taskId
        )
    }
}