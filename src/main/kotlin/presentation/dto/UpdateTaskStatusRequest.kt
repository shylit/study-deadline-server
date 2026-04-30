package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskStatusRequest(
    val status: String
)