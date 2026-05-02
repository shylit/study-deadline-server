package ru.mirea.shylit.studydeadline.domain.usecases

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
            else -> null
        }
    }
}