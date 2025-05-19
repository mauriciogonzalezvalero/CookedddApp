package com.example.cookeddd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button

    // Credenciales correctas
    private val CORRECT_USERNAME = "usuario"
    private val CORRECT_PASSWORD = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicializar vistas
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Configurar listener del botón de login
        btnLogin.setOnClickListener {
            validateCredentials()
        }
    }

    private fun validateCredentials() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validar que los campos no estén vacíos
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_fields_error), Toast.LENGTH_SHORT).show()
            return
        }

        // Validar credenciales
        if (username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
            // Credenciales correctas, ir a la pantalla principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar esta actividad para que no se pueda volver atrás
        } else {
            // Credenciales incorrectas
            Toast.makeText(this, getString(R.string.invalid_credentials_error), Toast.LENGTH_SHORT).show()
        }
    }
} 