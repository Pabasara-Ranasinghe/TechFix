package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ManagementDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_management_dashboard)

        auth = FirebaseAuth.getInstance()

        // ==========================================
        // MANAGE APPOINTMENTS
        // ==========================================

        val btnManageAppointments =
            findViewById<Button>(R.id.btnManageAppointments)

        btnManageAppointments.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ManageAppointmentsActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // MANAGE TECHNICIANS
        // ==========================================

        val btnManageTechnicians =
            findViewById<Button>(R.id.btnManageTechnicians)

        btnManageTechnicians.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ManageTechniciansActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // MANAGE SPARE PARTS
        // ==========================================

        val btnManageParts =
            findViewById<Button>(R.id.btnManageParts)

        btnManageParts.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ManageSparePartsActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // MANAGE REPAIR PRICES
        // ==========================================

        val btnManagePrices =
            findViewById<Button>(R.id.btnManagePrices)

        btnManagePrices.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ManageRepairPricesActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // MANAGE PAYMENTS
        // ==========================================

        val btnManagePayments =
            findViewById<Button>(R.id.btnManagePayments)

        btnManagePayments.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ManagePaymentsActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // LOGOUT
        // ==========================================

        val btnManagementLogout =
            findViewById<Button>(R.id.btnManagementLogout)

        btnManagementLogout.setOnClickListener {

            auth.signOut()

            val intent =
                Intent(
                    this,
                    LoginActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}