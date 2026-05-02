package ru.mirea.shylit.studydeadline.domain.usecases

import ru.mirea.shylit.studydeadline.domain.models.User
import ru.mirea.shylit.studydeadline.domain.repositories.UserRepository

class GetCurrentUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): User {
        return userRepository.getCurrentUser()
    }
}