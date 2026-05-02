package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.TasksTable
import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository

class PostgresTaskRepository : TaskRepository {

    override fun getAllTasks(): List<Task> {
        return transaction {
            TasksTable.selectAll().map { row ->
                Task(
                    id = row[TasksTable.id],
                    title = row[TasksTable.title],
                    description = row[TasksTable.description],
                    subject = "Предмет",
                    deadline = row[TasksTable.deadline].toString(),
                    status = TaskStatus.valueOf(row[TasksTable.status]),
                    priority = TaskPriority.valueOf(row[TasksTable.priority]),
                    type = TaskType.valueOf(row[TasksTable.type])
                )
            }
        }
    }

    override fun getTaskById(taskId: Int): Task? = TODO()

    override fun getTasksBySubject(subject: String): List<Task> = TODO()

    override fun getTasksForToday(today: String): List<Task> = TODO()

    override fun getTasksForWeek(
        startDate: String,
        endDate: String
    ): List<Task> = TODO()

    override fun searchTasks(
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task> = TODO()

    override fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task = TODO()

    override fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        status: TaskStatus,
        priority: TaskPriority,
        type: TaskType
    ): Task? = TODO()

    override fun updateTaskStatus(
        taskId: Int,
        status: TaskStatus
    ): Task? = TODO()

    override fun deleteTask(taskId: Int): Boolean = TODO()
}