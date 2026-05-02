package ru.mirea.shylit.studydeadline.domain.usecases

import java.time.LocalDate
import java.time.format.DateTimeParseException

class ValidateTaskUseCase {

    operator fun invoke(
        title: String,
        description: String,
        subject: String,
        deadline: String
    ): String? {
        return when {
            title.isBlank() -> "Название задания не может быть пустым"
            description.isBlank() -> "Описание задания не может быть пустым"
            subject.isBlank() -> "Предмет задания не может быть пустым"
            deadline.isBlank() -> "Дедлайн задания не может быть пустым"
            !isValidDate(deadline) -> "Дедлайн должен быть указан в формате YYYY-MM-DD"
            else -> null
        }
    }

    private fun isValidDate(value: String): Boolean {
        return try {
            LocalDate.parse(value)
            true
        } catch (exception: DateTimeParseException) {
            false
        }
    }
}