package com.example.mascotaiot

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.ValueEventListener

/**
 * VerAccionesActivity — Pantalla 5
 *
 * Muestra el historial de eventos y telemetría en tiempo real desde Firebase Realtime Database (/acciones).
 * Incluye activaciones de compuerta y detecciones de obstáculos del sensor ultrasónico/PIR del ESP32.
 */
class VerAccionesActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var rvAcciones: RecyclerView
    private var accionesListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_acciones)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        rvAcciones = findViewById(R.id.rvAcciones)
        rvAcciones.layoutManager = LinearLayoutManager(this)

        cargarDatosUsuario()
        iniciarEscuchaAcciones()

        findViewById<MaterialButton>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun cargarDatosUsuario() {
        val user = FirebaseRepository.currentUser
        if (user != null) {
            tvNombreUsuario.text = "${user.nombre} ${user.apellido}"
        }
    }

    private fun iniciarEscuchaAcciones() {
        // Carga inicial inmediata de la lista en memoria
        rvAcciones.adapter = AccionesAdapter(FirebaseRepository.listaAcciones)

        // Escucha en tiempo real de Firebase Realtime Database
        accionesListener = FirebaseRepository.escucharAcciones { listaActualizada ->
            rvAcciones.adapter = AccionesAdapter(listaActualizada)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        accionesListener?.let { FirebaseRepository.removerListenerAcciones(it) }
    }
}
