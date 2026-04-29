package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.Task

interface TaskRepository {
    fun getAllTasks(): List<Task>
}