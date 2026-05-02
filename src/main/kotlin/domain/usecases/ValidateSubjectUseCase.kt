package ru.mirea.shylit.studydeadline.domain.usecases

class ValidateSubjectUseCase {

    operator fun invoke(
        name: String,
        description: String
    ): String? {
        return when {
            name.isBlank() -> "Название предмета не может быть пустым"
            description.isBlank() -> "Описание предмета не может быть пустым"
            else -> null
        }
    }
}