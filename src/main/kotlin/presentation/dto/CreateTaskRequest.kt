package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val subject: String,
    val deadline: String,
    val priority: String = "MEDIUM",
    val type: String = "OTHER"
)