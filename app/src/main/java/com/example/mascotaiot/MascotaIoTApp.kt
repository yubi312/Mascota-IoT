package com.example.mascotaiot

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

/**
 * Clase Application principal para inicializar Firebase con persistencia offline.
 */
class MascotaIoTApp : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            FirebaseApp.initializeApp(this)
            // Persistencia offline de Firebase Realtime Database
            val db = try {
                FirebaseDatabase.getInstance("https://fir-mascotasiot-default-rtdb.firebaseio.com")
            } catch (e: Exception) {
                FirebaseDatabase.getInstance()
            }
            db.setPersistenceEnabled(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
