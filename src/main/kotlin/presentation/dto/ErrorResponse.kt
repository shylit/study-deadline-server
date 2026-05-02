package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)