package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Connect UI elements
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        // LOGIN BUTTON
        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Check empty fields
            if (email.isEmpty()) {
                etEmail.error = "Please enter your email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Please enter your password"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            // Firebase login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Login successful!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this,
                            CustomerHomeActivity::class.java
                        )

                        startActivity(intent)

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // REGISTER LINK
        tvRegister.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }
    }
}