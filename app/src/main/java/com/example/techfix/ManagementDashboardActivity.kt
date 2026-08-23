package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/*
 * ManagementDashboardActivity
 *
 * Main dashboard for TechFix management users.
 *
 * Management can access:
 *
 * 1. Appointments
 * 2. Technicians
 * 3. Spare Parts
 * 4. Repair Prices
 * 5. Payments
 */

class ManagementDashboardActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Display the management dashboard layout
        setContentView(R.layout.activity_management_dashboard)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // ==========================================
        // CONNECT BUTTONS
        // ==========================================

        val btnManageAppointments =
            findViewById<Button>(
                R.id.btnManageAppointments
            )

        val btnManageTechnicians =
            findViewById<Button>(
                R.id.btnManageTechnicians
            )

        val btnManageParts =
            findViewById<Button>(
                R.id.btnManageParts
            )

        val btnManagePrices =
            findViewById<Button>(
                R.id.btnManagePrices
            )

        val btnManagePayments =
            findViewById<Button>(
                R.id.btnManagePayments
            )

        val btnManagementLogout =
            findViewById<Button>(
                R.id.btnManagementLogout
            )

        // ==========================================
        // APPOINTMENTS
        // ==========================================

        btnManageAppointments.setOnClickListener {

            // Open the management appointments screen
            val intent =
                Intent(
                    this,
                    ManageAppointmentsActivity::class.java
                )

            startActivity(intent)
        }

        // ==========================================
        // TECHNICIANS
        // ==========================================

        btnManageTechnicians.setOnClickListener {

            // Open technician management
            val intent =
                Intent(
                    this,
                    ManageTechniciansActivity::class.java
                )

            startActivity(intent)
        }

        // ==========================================
        // SPARE PARTS
        // ==========================================

        btnManageParts.setOnClickListener {

            // Open spare parts management
            val intent =
                Intent(
                    this,
                    ManageSparePartsActivity::class.java
                )

            startActivity(intent)
        }

        // ==========================================
        // REPAIR PRICES
        // ==========================================

        btnManagePrices.setOnClickListener {

            Toast.makeText(
                this,
                "Repair price management coming next",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ==========================================
        // PAYMENTS
        // ==========================================

        btnManagePayments.setOnClickListener {

            Toast.makeText(
                this,
                "Payment management coming next",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ==========================================
        // LOGOUT
        // ==========================================

        btnManagementLogout.setOnClickListener {

            // Sign out from Firebase
            auth.signOut()

            // Return to login screen
            val intent =
                Intent(
                    this,
                    LoginActivity::class.java
                )

            // Clear previous screens
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}