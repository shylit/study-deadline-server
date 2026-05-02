package ru.mirea.shylit.studydeadline.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val firebaseUid: String,
    val email: String,
    val name: String
)