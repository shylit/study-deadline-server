package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateSubjectRequest(
    val name: String,
    val description: String
)