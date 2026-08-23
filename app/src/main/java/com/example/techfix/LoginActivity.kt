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

/*
 * LoginActivity
 *
 * Handles login for both:
 *
 * 1. Customers
 * 2. Management
 *
 * After successful Firebase Authentication,
 * the user's role is checked in Firestore.
 *
 * CUSTOMER -> CustomerHomeActivity
 * MANAGER  -> ManagementDashboardActivity
 */

class LoginActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Firebase Firestore
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load login screen
        setContentView(R.layout.activity_login)

        // ==========================================
        // INITIALIZE FIREBASE
        // ==========================================

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // ==========================================
        // CONNECT UI ELEMENTS
        // ==========================================

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val tvRegister =
            findViewById<TextView>(R.id.tvRegister)

        // ==========================================
        // LOGIN BUTTON
        // ==========================================

        btnLogin.setOnClickListener {

            // Get email
            val email =
                etEmail.text.toString().trim()

            // Get password
            val password =
                etPassword.text.toString().trim()

            // ======================================
            // VALIDATE EMAIL
            // ======================================

            if (email.isEmpty()) {

                etEmail.error =
                    "Please enter your email"

                etEmail.requestFocus()

                return@setOnClickListener
            }

            // ======================================
            // VALIDATE PASSWORD
            // ======================================

            if (password.isEmpty()) {

                etPassword.error =
                    "Please enter your password"

                etPassword.requestFocus()

                return@setOnClickListener
            }

            // Disable login button
            btnLogin.isEnabled = false
            btnLogin.text = "LOGGING IN..."

            // ======================================
            // FIREBASE AUTHENTICATION
            // ======================================

            auth.signInWithEmailAndPassword(
                email,
                password
            )
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        // Get logged-in Firebase user
                        val user =
                            auth.currentUser

                        if (user == null) {

                            btnLogin.isEnabled = true
                            btnLogin.text = "LOGIN"

                            Toast.makeText(
                                this,
                                "Login failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@addOnCompleteListener
                        }

                        // ==================================
                        // CHECK USER ROLE
                        // ==================================

                        firestore
                            .collection("customers")
                            .document(user.uid)
                            .get()

                            .addOnSuccessListener { document ->

                                if (document.exists()) {

                                    // Get role from Firestore
                                    val role =
                                        document.getString("role")

                                    // ==================================
                                    // MANAGEMENT LOGIN
                                    // ==================================

                                    if (role == "MANAGER") {

                                        Toast.makeText(
                                            this,
                                            "Management login successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent =
                                            Intent(
                                                this,
                                                ManagementDashboardActivity::class.java
                                            )

                                        startActivity(intent)

                                        finish()

                                    }

                                    // ==================================
                                    // CUSTOMER LOGIN
                                    // ==================================

                                    else if (role == "CUSTOMER") {

                                        Toast.makeText(
                                            this,
                                            "Login successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent =
                                            Intent(
                                                this,
                                                CustomerHomeActivity::class.java
                                            )

                                        startActivity(intent)

                                        finish()

                                    }

                                    // ==================================
                                    // UNKNOWN ROLE
                                    // ==================================

                                    else {

                                        btnLogin.isEnabled = true
                                        btnLogin.text = "LOGIN"

                                        Toast.makeText(
                                            this,
                                            "User role is not recognized.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                } else {

                                    btnLogin.isEnabled = true
                                    btnLogin.text = "LOGIN"

                                    Toast.makeText(
                                        this,
                                        "User profile not found.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            .addOnFailureListener { exception ->

                                btnLogin.isEnabled = true
                                btnLogin.text = "LOGIN"

                                Toast.makeText(
                                    this,
                                    "Could not check user role: ${exception.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                    } else {

                        // ==================================
                        // LOGIN FAILED
                        // ==================================

                        btnLogin.isEnabled = true
                        btnLogin.text = "LOGIN"

                        Toast.makeText(
                            this,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // ==========================================
        // REGISTER LINK
        // ==========================================

        tvRegister.setOnClickListener {

            val intent =
                Intent(
                    this,
                    RegisterActivity::class.java
                )

            startActivity(intent)
        }
    }
}