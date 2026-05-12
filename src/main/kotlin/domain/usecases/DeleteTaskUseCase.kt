package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        firebaseUid: String,
        taskId: Int
    ): Boolean {
        return taskRepository.deleteTask(
            firebaseUid = firebaseUid,
            taskId = taskId
        )
    }
}