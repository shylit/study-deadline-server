package ru.mirea.shylit.studydeadline.app.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseFactory {

    fun init() {

        if (FirebaseApp.getApps().isNotEmpty()) {
            return
        }

        val serviceAccount = FileInputStream(
            "firebase-service-account.json"
        )

        val options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(serviceAccount)
            )
            .build()

        FirebaseApp.initializeApp(options)
    }
}