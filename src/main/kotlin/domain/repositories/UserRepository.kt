package ru.mirea.shylit.studydeadline.domain.repositories

import ru.mirea.shylit.studydeadline.domain.models.User

interface UserRepository {
    fun getCurrentUser(): User
}