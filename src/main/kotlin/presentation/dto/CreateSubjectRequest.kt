package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateSubjectRequest(
    val name: String,
    val description: String
)