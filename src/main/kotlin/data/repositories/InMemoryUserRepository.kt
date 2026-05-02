package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.User
import ru.mirea.shylit.studydeadline.domain.repositories.UserRepository

class InMemoryUserRepository : UserRepository {

    override fun getCurrentUser(): User {
        return User(
            id = 1,
            firebaseUid = "demo-firebase-uid",
            email = "student@example.com",
            name = "Студент"
        )
    }
}