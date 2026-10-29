package com.example.mascotaiot

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * VerPerfilActivity — Pantalla 7
 *
 * Muestra el perfil completo del usuario autenticado en Firebase.
 * Conforme a la norma ISO 27400 para seguridad IoT, la contraseña nunca se expone
 * en texto plano en la interfaz de usuario.
 */
class VerPerfilActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvRut: TextView
    private lateinit var tvNombre: TextView
    private lateinit var tvApellido: TextView
    private lateinit var tvCorreo: TextView
    private lateinit var tvCargo: TextView
    private lateinit var tvContrasena: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_perfil)

        inicializarVistas()
        cargarDatosUsuario()

        findViewById<MaterialButton>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun inicializarVistas() {
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvRut = findViewById(R.id.tvRut)
        tvNombre = findViewById(R.id.tvNombre)
        tvApellido = findViewById(R.id.tvApellido)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvCargo = findViewById(R.id.tvCargo)
        tvContrasena = findViewById(R.id.tvContrasena)
    }

    private fun cargarDatosUsuario() {
        val user = FirebaseRepository.currentUser
        if (user != null) {
            tvNombreUsuario.text = "${user.nombre} ${user.apellido}"
            tvRut.text = user.rut
            tvNombre.text = user.nombre
            tvApellido.text = user.apellido
            tvCorreo.text = user.correo
            tvCargo.text = user.cargo
            tvContrasena.text = "••••••••"
        }
    }
}
