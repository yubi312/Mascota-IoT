package com.example.mascotaiot

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * MenuActivity — Pantalla 3
 *
 * Muestra el nombre del usuario logueado desde Firebase.
 * Redirige a las 4 opciones principales del sistema MascotaIoT.
 */
class MenuActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)

        cargarNombreUsuario()
        configurarBotones()
    }

    override fun onResume() {
        super.onResume()
        cargarNombreUsuario()
    }

    private fun cargarNombreUsuario() {
        val user = FirebaseRepository.currentUser
        if (user != null) {
            tvNombreUsuario.text = "${user.nombre} ${user.apellido}"
        } else {
            tvNombreUsuario.text = "Usuario MascotaIoT"
        }
    }

    private fun configurarBotones() {
        findViewById<MaterialButton>(R.id.btnManipularCircuito).setOnClickListener {
            startActivity(Intent(this, ManipularCircuitoActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnVerAcciones).setOnClickListener {
            startActivity(Intent(this, VerAccionesActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnVerEstadisticas).setOnClickListener {
            startActivity(Intent(this, EstadisticasActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnVerPerfil).setOnClickListener {
            startActivity(Intent(this, VerPerfilActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnCerrarSesion).setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        FirebaseRepository.logout()
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
