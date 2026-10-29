package com.example.mascotaiot

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.ValueEventListener

/**
 * EstadisticasActivity — Pantalla 6
 *
 * Muestra las estadísticas agregadas calculadas en tiempo real desde Firebase Realtime Database:
 *   - Activaciones de compuerta
 *   - Desactivaciones de compuerta
 *   - Obstáculos / Mascotas detectadas por sensor IoT
 *   - Distancia total acumulada (en cm)
 */
class EstadisticasActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvActivaciones: TextView
    private lateinit var tvDesactivaciones: TextView
    private lateinit var tvObstaculos: TextView
    private lateinit var tvDistanciaTotal: TextView

    private var statsListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        inicializarVistas()
        cargarDatosUsuario()
        actualizarEstadisticas()
        iniciarEscuchaEstadisticas()

        findViewById<MaterialButton>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun inicializarVistas() {
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvActivaciones = findViewById(R.id.tvActivaciones)
        tvDesactivaciones = findViewById(R.id.tvDesactivaciones)
        tvObstaculos = findViewById(R.id.tvObstaculos)
        tvDistanciaTotal = findViewById(R.id.tvDistanciaTotal)
    }

    private fun cargarDatosUsuario() {
        val user = FirebaseRepository.currentUser
        if (user != null) {
            tvNombreUsuario.text = "${user.nombre} ${user.apellido}"
        }
    }

    private fun iniciarEscuchaEstadisticas() {
        statsListener = FirebaseRepository.escucharEstadisticas { stats ->
            tvActivaciones.text = stats.activaciones.toString()
            tvDesactivaciones.text = stats.desactivaciones.toString()
            tvObstaculos.text = stats.obstaculos.toString()
            tvDistanciaTotal.text = "${stats.distanciaTotal} cm"
        }
    }

    private fun actualizarEstadisticas() {
        val stats = FirebaseRepository.getEstadisticas()
        tvActivaciones.text = stats.activaciones.toString()
        tvDesactivaciones.text = stats.desactivaciones.toString()
        tvObstaculos.text = stats.obstaculos.toString()
        tvDistanciaTotal.text = "${stats.distanciaTotal} cm"
    }

    override fun onDestroy() {
        super.onDestroy()
        statsListener?.let { FirebaseRepository.removerListenerEstadisticas(it) }
    }
}
