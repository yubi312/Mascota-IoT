package com.example.mascotaiot

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Modelo de datos de Usuario para Firebase Authentication y Realtime Database.
 */
@IgnoreExtraProperties
data class UsuarioModel(
    val uid: String = "",
    val rut: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val cargo: String = "",
    val contrasena: String = ""
)
