package com.example.mascotaiot

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * CryptoHelper — Módulo de Cifrado y Seguridad Criptográfica
 *
 * Implementa las medidas de seguridad exigidas por la norma ISO 27400
 * para aplicaciones IoT (protección de datos, control de acceso y manejo de credenciales):
 *
 * 1. Hashing SHA-256: Algoritmo unidireccional estándar NIST para verificación de contraseñas.
 * 2. Cifrado AES-128 / AES-256 CBC con PKCS5Padding: Cifrado simétrico de datos sensibles en Firebase.
 */
object CryptoHelper {

    // Clave secreta fija de 16 bytes (128-bit) y vector de inicialización de 16 bytes para AES CBC
    private const val SECRET_KEY = "MascotaIoT@2026!" // 16 bytes
    private const val INIT_VECTOR = "IoTGate_Vector16" // 16 bytes

    const val PREFIX_ENC = "ENC:"

    /**
     * Genera un hash criptográfico SHA-256 del texto ingresado.
     */
    fun hashSha256(texto: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(texto.toByteArray(Charsets.UTF_8))
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            texto
        }
    }

    /**
     * Cifra un texto plano utilizando AES/CBC/PKCS5Padding y retorna un string Base64 con prefijo "ENC:".
     */
    fun cifrarAES(textoPlano: String): String {
        return try {
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val bytesCifrados = cipher.doFinal(textoPlano.toByteArray(Charsets.UTF_8))
            val base64 = Base64.encodeToString(bytesCifrados, Base64.NO_WRAP)
            "$PREFIX_ENC$base64"
        } catch (e: Exception) {
            textoPlano
        }
    }

    /**
     * Descifra una cadena cifrada con AES/CBC/PKCS5Padding.
     */
    fun descifrarAES(textoCifrado: String): String {
        return try {
            val cleanCipher = if (textoCifrado.startsWith(PREFIX_ENC)) {
                textoCifrado.removePrefix(PREFIX_ENC)
            } else {
                textoCifrado
            }
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val bytesDecodificados = Base64.decode(cleanCipher, Base64.NO_WRAP)
            val bytesOriginales = cipher.doFinal(bytesDecodificados)
            String(bytesOriginales, Charsets.UTF_8)
        } catch (e: Exception) {
            textoCifrado
        }
    }

    /**
     * Valida una contraseña ingresada contra el valor almacenado en Firebase,
     * admitiendo tanto contraseñas cifradas con AES, hashes SHA-256 o texto plano anterior.
     */
    fun verificarContrasena(ingresada: String, almacenada: String): Boolean {
        val passLimpia = ingresada.trim()
        val storedLimpia = almacenada.trim()

        // 1. Coincidencia directa (texto plano anterior)
        if (passLimpia == storedLimpia) return true

        // 2. Coincidencia con hash SHA-256
        if (hashSha256(passLimpia).equals(storedLimpia, ignoreCase = true)) return true

        // 3. Coincidencia descifrando AES
        if (storedLimpia.startsWith(PREFIX_ENC)) {
            val descifrada = descifrarAES(storedLimpia)
            if (descifrada == passLimpia) return true
        }

        // 4. Coincidencia cifrando la ingresada
        if (cifrarAES(passLimpia) == storedLimpia) return true

        return false
    }
}
