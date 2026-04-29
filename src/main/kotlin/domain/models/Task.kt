package ru.mirea.shylit.studydeadline.domain.models

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val subject: String,
    val deadline: String,
    val isCompleted: Boolean
)