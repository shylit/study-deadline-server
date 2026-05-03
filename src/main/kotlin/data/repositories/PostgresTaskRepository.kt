package ru.mirea.shylit.studydeadline.data.repositories

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.mirea.shylit.studydeadline.data.tables.SubjectsTable
import ru.mirea.shylit.studydeadline.data.tables.TasksTable
import ru.mirea.shylit.studydeadline.data.tables.UsersTable
import ru.mirea.shylit.studydeadline.domain.models.Task
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import java.time.LocalDate
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.update

class PostgresTaskRepository : TaskRepository {

    override fun getAllTasks(): List<Task> {
        return transaction {
            TasksTable.selectAll().map { row ->
                Task(
                    id = row[TasksTable.id],
                    title = row[TasksTable.title],
                    description = row[TasksTable.description],
                    subject = getSubjectName(row[TasksTable.subjectId]),
                    deadline = row[TasksTable.deadline].toString(),
                    status = TaskStatus.valueOf(row[TasksTable.status]),
                    priority = TaskPriority.valueOf(row[TasksTable.priority]),
                    type = TaskType.valueOf(row[TasksTable.type])
                )
            }
        }
    }

    override fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Task {
        return transaction {
            val userId = getOrCreateDemoUser()
            val subjectId = getOrCreateSubject(
                userId = userId,
                subjectName = subject
            )

            val taskId = TasksTable.insert { row ->
                row[TasksTable.userId] = userId
                row[TasksTable.subjectId] = subjectId
                row[TasksTable.title] = title
                row[TasksTable.description] = description
                row[TasksTable.deadline] = LocalDate.parse(deadline)
                row[TasksTable.status] = TaskStatus.PLANNED.name
                row[TasksTable.priority] = priority.name
                row[TasksTable.type] = type.name
            } get TasksTable.id

            Task(
                id = taskId,
                title = title,
                description = description,
                subject = subject,
                deadline = deadline,
                status = TaskStatus.PLANNED,
                priority = priority,
                type = type
            )
        }
    }

    override fun getTaskById(taskId: Int): Task? {
        return transaction {

            TasksTable
                .selectAll()
                .where { TasksTable.id eq taskId }
                .map { row ->
                    Task(
                        id = row[TasksTable.id],
                        title = row[TasksTable.title],
                        description = row[TasksTable.description],
                        subject = getSubjectName(row[TasksTable.subjectId]),
                        deadline = row[TasksTable.deadline].toString(),
                        status = TaskStatus.valueOf(row[TasksTable.status]),
                        priority = TaskPriority.valueOf(row[TasksTable.priority]),
                        type = TaskType.valueOf(row[TasksTable.type])
                    )
                }
                .singleOrNull()
        }
    }

    override fun getTasksBySubject(subject: String): List<Task> {
        return transaction {
            getAllTasks().filter { task ->
                task.subject.equals(subject, ignoreCase = true)
            }
        }
    }

    override fun getTasksForToday(today: String): List<Task> {
        return transaction {
            getAllTasks().filter { task ->
                task.deadline == today
            }
        }
    }

    override fun getTasksForWeek(
        startDate: String,
        endDate: String
    ): List<Task> {
        return transaction {
            getAllTasks().filter { task ->
                task.deadline in startDate..endDate
            }
        }
    }

    override fun searchTasks(
        query: String?,
        status: TaskStatus?,
        priority: TaskPriority?
    ): List<Task> {
        return transaction {
            getAllTasks().filter { task ->
                val matchesQuery = query.isNullOrBlank() ||
                        task.title.contains(query, ignoreCase = true) ||
                        task.description.contains(query, ignoreCase = true) ||
                        task.subject.contains(query, ignoreCase = true)

                val matchesStatus = status == null || task.status == status
                val matchesPriority = priority == null || task.priority == priority

                matchesQuery && matchesStatus && matchesPriority
            }
        }
    }

    override fun updateTask(
        taskId: Int,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        status: TaskStatus,
        priority: TaskPriority,
        type: TaskType
    ): Task? {

        return transaction {

            val existingTask = TasksTable
                .selectAll()
                .where { TasksTable.id eq taskId }
                .singleOrNull()

            if (existingTask == null) {
                return@transaction null
            }

            val userId = existingTask[TasksTable.userId]

            val subjectId = getOrCreateSubject(
                userId = userId,
                subjectName = subject
            )

            TasksTable.update(
                where = { TasksTable.id eq taskId }
            ) { row ->

                row[TasksTable.subjectId] = subjectId
                row[TasksTable.title] = title
                row[TasksTable.description] = description
                row[TasksTable.deadline] = LocalDate.parse(deadline)
                row[TasksTable.status] = status.name
                row[TasksTable.priority] = priority.name
                row[TasksTable.type] = type.name
            }

            getTaskById(taskId)
        }
    }

    override fun updateTaskStatus(
        taskId: Int,
        status: TaskStatus
    ): Task? {
        return transaction {
            val updatedRows = TasksTable.update(
                where = { TasksTable.id eq taskId }
            ) { row ->
                row[TasksTable.status] = status.name
            }

            if (updatedRows == 0) {
                null
            } else {
                getTaskById(taskId)
            }
        }
    }

    override fun deleteTask(taskId: Int): Boolean {
        return transaction {
            val deletedRows = TasksTable.deleteWhere {
                TasksTable.id eq taskId
            }

            deletedRows > 0
        }
    }

    private fun getOrCreateDemoUser(): Int {
        val existingUser = UsersTable
            .selectAll()
            .where { UsersTable.firebaseUid eq "demo-firebase-uid" }
            .map { row -> row[UsersTable.id] }
            .singleOrNull()

        if (existingUser != null) {
            return existingUser
        }

        return UsersTable.insert { row ->
            row[firebaseUid] = "demo-firebase-uid"
            row[email] = "student@example.com"
            row[name] = "Студент"
        } get UsersTable.id
    }

    private fun getOrCreateSubject(
        userId: Int,
        subjectName: String
    ): Int {
        val existingSubject = SubjectsTable
            .selectAll()
            .where {
                (SubjectsTable.userId eq userId) and
                        (SubjectsTable.name eq subjectName)
            }
            .map { row -> row[SubjectsTable.id] }
            .singleOrNull()

        if (existingSubject != null) {
            return existingSubject
        }

        return SubjectsTable.insert { row ->
            row[SubjectsTable.userId] = userId
            row[name] = subjectName
            row[description] = "Предмет создан автоматически при добавлении задания"
        } get SubjectsTable.id
    }

    private fun getSubjectName(subjectId: Int): String {
        return SubjectsTable
            .selectAll()
            .where { SubjectsTable.id eq subjectId }
            .map { row -> row[SubjectsTable.name] }
            .singleOrNull()
            ?: "Предмет"
    }
}