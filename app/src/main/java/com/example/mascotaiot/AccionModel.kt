package com.example.mascotaiot

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Modelo de datos para una Acción registrada en Firebase Realtime Database.
 */
@IgnoreExtraProperties
data class AccionModel(
    val id: String = "",
    val fecha: String = "",
    val hora: String = "",
    val usuario: String = "",
    val accion: String = "",
    val distancia: String = ""
)
