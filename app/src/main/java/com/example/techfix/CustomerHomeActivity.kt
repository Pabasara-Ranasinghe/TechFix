package com.example.techfix

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerHomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect to customer home layout
        setContentView(R.layout.activity_customer_home)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Connect UI elements
        val tvHomeWelcome =
            findViewById<TextView>(R.id.tvHomeWelcome)

        val btnServices =
            findViewById<Button>(R.id.btnServices)

        val btnAppointments =
            findViewById<Button>(R.id.btnAppointments)

        val btnHistory =
            findViewById<Button>(R.id.btnHistory)

        val btnBranches =
            findViewById<Button>(R.id.btnBranches)

        // Get currently logged-in customer
        val currentUser = auth.currentUser

        if (currentUser != null) {

            // Get customer profile from Firestore
            firestore.collection("customers")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->

                    if (document.exists()) {

                        val name =
                            document.getString("name")

                        if (!name.isNullOrEmpty()) {

                            tvHomeWelcome.text =
                                "Welcome, $name!"
                        }
                    }
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Could not load your profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        // Repair Services
        btnServices.setOnClickListener {

            Toast.makeText(
                this,
                "Repair Services coming next",
                Toast.LENGTH_SHORT
            ).show()
        }

        // My Appointments
        btnAppointments.setOnClickListener {

            Toast.makeText(
                this,
                "My Appointments coming next",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Repair History
        btnHistory.setOnClickListener {

            Toast.makeText(
                this,
                "Repair History coming next",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Branches & Locations
        btnBranches.setOnClickListener {

            Toast.makeText(
                this,
                "Branches & Locations coming next",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}