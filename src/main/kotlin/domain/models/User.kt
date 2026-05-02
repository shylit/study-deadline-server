package ru.mirea.shylit.studydeadline.domain.models

data class User(
    val id: Int,
    val firebaseUid: String,
    val email: String,
    val name: String
)