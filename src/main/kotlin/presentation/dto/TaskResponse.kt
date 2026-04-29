package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val subject: String,
    val deadline: String,
    val status: String,
    val priority: String,
    val type: String
)