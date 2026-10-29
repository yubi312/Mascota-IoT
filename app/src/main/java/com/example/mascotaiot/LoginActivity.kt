package com.example.mascotaiot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
 * LoginActivity — Pantalla 1
 *
 * Autenticación mediante Firebase Authentication y Realtime Database.
 * Permite ingresar por RUT o por Correo Electrónico.
 * Cuentas demo pre-configuradas:
 *   - RUT: 11.111.111-1 | Clave: 123456 (Carlos Riveros)
 *   - RUT: 22.222.222-2 | Clave: 123456 (Agustín Vidal)
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var etRut: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnIniciarSesion: MaterialButton
    private lateinit var btnCrearCuenta: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Si ya hay sesión activa
        if (FirebaseRepository.currentUser != null) {
            navegarAlMenu()
            return
        }

        inicializarVistas()
        configurarBotones()
    }

    private fun inicializarVistas() {
        etRut = findViewById(R.id.etRut)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)

        // Prellenar con credencial para prueba inmediata
        etRut.setText("11.111.111-1")
        etContrasena.setText("123456")
    }

    private fun configurarBotones() {
        btnIniciarSesion.setOnClickListener {
            val rut = etRut.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (validarCampos(rut, contrasena)) {
                btnIniciarSesion.isEnabled = false
                btnIniciarSesion.text = "Iniciando..."

                FirebaseRepository.login(
                    identificador = rut,
                    contrasena = contrasena,
                    onSuccess = { usuario ->
                        btnIniciarSesion.isEnabled = true
                        btnIniciarSesion.text = "Iniciar Sesión"
                        Toast.makeText(this, "Bienvenido, ${usuario.nombre} ${usuario.apellido}", Toast.LENGTH_SHORT).show()
                        navegarAlMenu()
                    },
                    onError = { mensajeError ->
                        btnIniciarSesion.isEnabled = true
                        btnIniciarSesion.text = "Iniciar Sesión"
                        Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
                    }
                )
            }
        }

        btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }
    }

    private fun validarCampos(rut: String, contrasena: String): Boolean {
        if (rut.isEmpty()) {
            etRut.error = "Ingrese su RUT o Correo"
            return false
        }
        if (contrasena.isEmpty()) {
            etContrasena.error = "Ingrese su contraseña"
            return false
        }
        return true
    }

    private fun navegarAlMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}
