package ru.mirea.shylit.studydeadline.domain.exceptions

class UnauthorizedException(
    message: String = "Пользователь не авторизован"
) : RuntimeException(message)