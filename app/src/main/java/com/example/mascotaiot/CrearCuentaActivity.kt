package com.example.mascotaiot

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
 * CrearCuentaActivity — Pantalla 2
 *
 * Registra un nuevo usuario en Firebase Realtime Database bajo /Usuario/{pushKey}.
 * Contraseña cifrada con AES-128-CBC conforme a ISO 27400.
 */
class CrearCuentaActivity : AppCompatActivity() {

    private val TAG = "MascotaIoT_CC"

    private lateinit var etRut: TextInputEditText
    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellido: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var spinnerCargo: AutoCompleteTextView
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etRepetirContrasena: TextInputEditText
    private lateinit var btnRegistrar: MaterialButton
    private lateinit var btnVolver: MaterialButton
    private lateinit var progressBar: ProgressBar

    private val cargos = listOf("Administrador", "Técnico", "Operador", "Supervisor", "Visitante")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        inicializarVistas()
        configurarSpinnerCargo()
        configurarBotones()
    }

    private fun inicializarVistas() {
        etRut               = findViewById(R.id.etRut)
        etNombre            = findViewById(R.id.etNombre)
        etApellido          = findViewById(R.id.etApellido)
        etCorreo            = findViewById(R.id.etCorreo)
        spinnerCargo        = findViewById(R.id.spinnerCargo)
        etContrasena        = findViewById(R.id.etContrasena)
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena)
        btnRegistrar        = findViewById(R.id.btnRegistrar)
        btnVolver           = findViewById(R.id.btnVolver)
        progressBar         = findViewById(R.id.progressBar)
    }

    private fun configurarSpinnerCargo() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cargos)
        spinnerCargo.setAdapter(adapter)
        spinnerCargo.setText(cargos[0], false) // Selecciona "Administrador" por defecto
    }

    private fun configurarBotones() {
        btnRegistrar.setOnClickListener {
            Log.d(TAG, "btnRegistrar presionado")
            val datos = obtenerDatosFormulario()
            if (datos != null) {
                ejecutarRegistro(datos)
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    /**
     * Lee y valida todos los campos. Retorna un UsuarioModel si todo está OK, null si hay error.
     */
    private fun obtenerDatosFormulario(): UsuarioModel? {
        val rut        = etRut.text.toString().trim()
        val nombre     = etNombre.text.toString().trim()
        val apellido   = etApellido.text.toString().trim()
        val correo     = etCorreo.text.toString().trim()
        val cargo      = spinnerCargo.text.toString().trim().ifEmpty { cargos[0] }
        val contrasena = etContrasena.text.toString()
        val repetir    = etRepetirContrasena.text.toString()

        Log.d(TAG, "Formulario → RUT:'$rut' Nombre:'$nombre' Correo:'$correo' Cargo:'$cargo'")

        // Validaciones con mensajes claros
        if (rut.isEmpty()) {
            etRut.error = "Ingrese RUT (ej: 12.345.678-9)"
            etRut.requestFocus()
            return null
        }
        if (nombre.isEmpty()) {
            etNombre.error = "Ingrese su nombre"
            etNombre.requestFocus()
            return null
        }
        if (apellido.isEmpty()) {
            etApellido.error = "Ingrese su apellido"
            etApellido.requestFocus()
            return null
        }
        if (correo.isEmpty() || !correo.contains("@")) {
            etCorreo.error = "Ingrese un correo válido (ej: usuario@correo.com)"
            etCorreo.requestFocus()
            return null
        }
        if (contrasena.length < 6) {
            etContrasena.error = "La contraseña debe tener al menos 6 caracteres"
            etContrasena.requestFocus()
            return null
        }
        if (contrasena != repetir) {
            etRepetirContrasena.error = "Las contraseñas no coinciden"
            etRepetirContrasena.requestFocus()
            return null
        }

        return UsuarioModel(
            rut        = rut,
            nombre     = nombre,
            apellido   = apellido,
            correo     = correo,
            cargo      = cargo,
            contrasena = contrasena
        )
    }

    /**
     * Llama a FirebaseRepository para guardar el usuario.
     * Muestra ProgressBar mientras espera la respuesta de Firebase.
     */
    private fun ejecutarRegistro(usuario: UsuarioModel) {
        Log.d(TAG, "Iniciando registro Firebase para RUT: ${usuario.rut}")

        // Bloquear UI y mostrar progreso
        btnRegistrar.isEnabled = false
        btnRegistrar.text = "Registrando..."
        progressBar.visibility = View.VISIBLE

        FirebaseRepository.registrarUsuario(
            usuario   = usuario,
            onSuccess = {
                // Ejecutar en hilo principal
                runOnUiThread {
                    Log.d(TAG, "✅ Registro exitoso")
                    progressBar.visibility = View.GONE
                    btnRegistrar.isEnabled = true
                    btnRegistrar.text = getString(R.string.btn_registrar)
                    Toast.makeText(
                        this,
                        "✅ ¡Usuario ${usuario.nombre} registrado exitosamente!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            },
            onError = { mensajeError ->
                // Ejecutar en hilo principal
                runOnUiThread {
                    Log.e(TAG, "❌ Error al registrar: $mensajeError")
                    progressBar.visibility = View.GONE
                    btnRegistrar.isEnabled = true
                    btnRegistrar.text = getString(R.string.btn_registrar)
                    Toast.makeText(
                        this,
                        "❌ $mensajeError",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }
}
