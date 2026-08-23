package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        // Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Connect UI elements
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        // CREATE ACCOUNT
        btnRegister.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validate name
            if (name.isEmpty()) {
                etName.error = "Please enter your name"
                etName.requestFocus()
                return@setOnClickListener
            }

            // Validate email
            if (email.isEmpty()) {
                etEmail.error = "Please enter your email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            // Validate phone
            if (phone.isEmpty()) {
                etPhone.error = "Please enter your phone number"
                etPhone.requestFocus()
                return@setOnClickListener
            }

            // Validate password
            if (password.isEmpty()) {
                etPassword.error = "Please enter a password"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error =
                    "Password must contain at least 6 characters"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            // Disable button while registering
            btnRegister.isEnabled = false
            btnRegister.text = "CREATING ACCOUNT..."

            // Create Firebase Authentication account
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val user = auth.currentUser

                        if (user != null) {

                            // Customer data for Firestore
                            val customer = hashMapOf(
                                "uid" to user.uid,
                                "name" to name,
                                "email" to email,
                                "phone" to phone,
                                "role" to "CUSTOMER",
                                "createdAt" to System.currentTimeMillis()
                            )

                            // Save customer profile
                            firestore.collection("customers")
                                .document(user.uid)
                                .set(customer)
                                .addOnSuccessListener {

                                    Toast.makeText(
                                        this,
                                        "Account created successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Go to Customer Home
                                    val intent = Intent(
                                        this,
                                        CustomerHomeActivity::class.java
                                    )

                                    startActivity(intent)

                                    finish()
                                }
                                .addOnFailureListener { exception ->

                                    btnRegister.isEnabled = true
                                    btnRegister.text = "CREATE ACCOUNT"

                                    Toast.makeText(
                                        this,
                                        "Profile could not be saved: ${exception.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                    } else {

                        btnRegister.isEnabled = true
                        btnRegister.text = "CREATE ACCOUNT"

                        Toast.makeText(
                            this,
                            "Registration failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // LOGIN LINK
        tvLogin.setOnClickListener {

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            startActivity(intent)

            finish()
        }
    }
}