package com.example.mascotaiot

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.ValueEventListener

/**
 * ManipularCircuitoActivity — Pantalla 4
 *
 * Control y monitoreo en tiempo real de la compuerta automatizada para mascotas.
 * Se comunica bidireccionalmente con Firebase Realtime Database (/circuito/estado, /circuito/led)
 * para interactuar con el microcontrolador ESP32 y el servomotor.
 */
class ManipularCircuitoActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvEstadoCircuito: TextView
    private lateinit var btnActivar: MaterialButton
    private lateinit var btnDesactivar: MaterialButton
    private lateinit var viewLed: View

    private var estadoListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipular_circuito)

        inicializarVistas()
        cargarDatosUsuario()
        actualizarVistaEstado(FirebaseRepository.estadoCircuito)
        configurarBotones()
        iniciarEscuchaTiempoReal()
    }

    private fun inicializarVistas() {
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvEstadoCircuito = findViewById(R.id.tvEstadoCircuito)
        btnActivar = findViewById(R.id.btnActivar)
        btnDesactivar = findViewById(R.id.btnDesactivar)
        viewLed = findViewById(R.id.viewLed)
    }

    private fun cargarDatosUsuario() {
        val user = FirebaseRepository.currentUser
        if (user != null) {
            tvNombreUsuario.text = "${user.nombre} ${user.apellido}"
        }
    }

    private fun iniciarEscuchaTiempoReal() {
        estadoListener = FirebaseRepository.escucharEstadoCircuito { nuevoEstado ->
            actualizarVistaEstado(nuevoEstado)
        }
    }

    private fun actualizarVistaEstado(estado: String) {
        tvEstadoCircuito.text = estado.replaceFirstChar { it.uppercase() }

        if (estado.equals("activado", ignoreCase = true)) {
            viewLed.setBackgroundResource(R.drawable.bg_led_activo)
        } else {
            viewLed.setBackgroundResource(R.drawable.bg_led_rojo)
        }
    }

    private fun configurarBotones() {
        btnActivar.setOnClickListener {
            FirebaseRepository.cambiarEstadoCircuito("activado") {
                Toast.makeText(this, "Compuerta ACTIVADA (Abierta - ESP32)", Toast.LENGTH_SHORT).show()
            }
        }

        btnDesactivar.setOnClickListener {
            FirebaseRepository.cambiarEstadoCircuito("desactivado") {
                Toast.makeText(this, "Compuerta DESACTIVADA (Cerrada - ESP32)", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<MaterialButton>(R.id.btnVolver).setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        estadoListener?.let { FirebaseRepository.removerListenerEstadoCircuito(it) }
    }
}
