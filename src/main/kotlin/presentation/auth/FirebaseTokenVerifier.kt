package ru.mirea.shylit.studydeadline.presentation.auth

import com.google.firebase.auth.FirebaseAuth

object FirebaseTokenVerifier {

    fun verify(token: String): UserSession? {

        return try {

            val decodedToken =
                FirebaseAuth.getInstance()
                    .verifyIdToken(token)

            println("Firebase UID from token: ${decodedToken.uid}")

            UserSession(
                firebaseUid = decodedToken.uid
            )

        } catch (e: Exception) {

            null
        }
    }
}