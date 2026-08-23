package com.example.techfix

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

/*
 * ManageAppointmentsActivity
 *
 * Management can use this screen to:
 *
 * 1. View all customer repair appointments
 * 2. View device information
 * 3. View the repair problem
 * 4. View preferred date
 * 5. View current status
 * 6. Update the repair status
 */

class ManageAppointmentsActivity : AppCompatActivity() {

    // Firestore database
    private lateinit var firestore: FirebaseFirestore

    // RecyclerView
    private lateinit var recyclerView: RecyclerView

    // Appointment list
    private val appointmentList =
        mutableListOf<Appointment>()

    // Adapter
    private lateinit var adapter:
            ManageAppointmentAdapter


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        // Load management appointment layout
        setContentView(
            R.layout.activity_manage_appointments
        )

        // ==========================================
        // INITIALIZE FIRESTORE
        // ==========================================

        firestore =
            FirebaseFirestore.getInstance()

        // ==========================================
        // CONNECT RECYCLER VIEW
        // ==========================================

        recyclerView =
            findViewById(
                R.id.rvManageAppointments
            )

        // Set vertical list
        recyclerView.layoutManager =
            LinearLayoutManager(this)

        // ==========================================
        // CREATE ADAPTER
        // ==========================================

        adapter =
            ManageAppointmentAdapter(
                appointmentList
            ) { appointment ->

                // Open status selection
                showStatusDialog(
                    appointment
                )
            }

        recyclerView.adapter =
            adapter

        // ==========================================
        // LOAD APPOINTMENTS
        // ==========================================

        loadAppointments()
    }


    // ==============================================
    // LOAD APPOINTMENTS FROM FIRESTORE
    // ==============================================

    private fun loadAppointments() {

        firestore
            .collection("appointments")
            .get()

            .addOnSuccessListener { result ->

                // Clear existing list
                appointmentList.clear()

                // Read every appointment
                for (document in result.documents) {

                    val appointment =
                        Appointment(

                            id = document.id,

                            customerId =
                                document.getString(
                                    "customerId"
                                ) ?: "",

                            deviceType =
                                document.getString(
                                    "deviceType"
                                ) ?: "",

                            deviceModel =
                                document.getString(
                                    "deviceModel"
                                ) ?: "",

                            problem =
                                document.getString(
                                    "problem"
                                ) ?: "",

                            preferredDate =
                                document.getString(
                                    "preferredDate"
                                ) ?: "",

                            status =
                                document.getString(
                                    "status"
                                ) ?: "Pending",

                            createdAt =
                                document.getLong(
                                    "createdAt"
                                ) ?: 0L
                        )

                    // Add appointment to list
                    appointmentList.add(
                        appointment
                    )
                }

                // Tell adapter that data changed
                adapter.notifyDataSetChanged()

                // Show message if there are no bookings
                if (appointmentList.isEmpty()) {

                    Toast.makeText(
                        this,
                        "No appointments found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            .addOnFailureListener { exception ->

                Toast.makeText(
                    this,
                    "Could not load appointments: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    // ==============================================
    // SHOW STATUS OPTIONS
    // ==============================================

    private fun showStatusDialog(
        appointment: Appointment
    ) {

        /*
         * Status options required for the
         * repair tracking process.
         */
        val statuses = arrayOf(
            "Pending",
            "Approved",
            "Repairing",
            "Ready for Collection",
            "Completed"
        )

        // Create dialog
        AlertDialog.Builder(this)

            .setTitle(
                "Update Repair Status"
            )

            .setItems(
                statuses
            ) { _, selectedIndex ->

                // Get selected status
                val newStatus =
                    statuses[selectedIndex]

                // Update Firestore
                updateAppointmentStatus(
                    appointment,
                    newStatus
                )
            }

            .setNegativeButton(
                "CANCEL",
                null
            )

            .show()
    }


    // ==============================================
    // UPDATE APPOINTMENT STATUS
    // ==============================================

    private fun updateAppointmentStatus(
        appointment: Appointment,
        newStatus: String
    ) {

        firestore
            .collection("appointments")
            .document(appointment.id)

            .update(
                "status",
                newStatus
            )

            .addOnSuccessListener {

                // Update local object
                appointment.status =
                    newStatus

                // Refresh list
                adapter.notifyDataSetChanged()

                Toast.makeText(
                    this,
                    "Status updated to $newStatus",
                    Toast.LENGTH_SHORT
                ).show()
            }

            .addOnFailureListener { exception ->

                Toast.makeText(
                    this,
                    "Could not update status: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}