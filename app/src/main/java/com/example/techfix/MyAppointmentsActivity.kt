package com.example.techfix

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/*
 * MyAppointmentsActivity
 *
 * This screen loads the logged-in customer's repair
 * appointments from Firestore and displays them
 * using a RecyclerView.
 */

class MyAppointmentsActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Firebase Firestore
    private lateinit var firestore: FirebaseFirestore

    // RecyclerView for displaying appointments
    private lateinit var recyclerAppointments: RecyclerView

    // Message shown when there are no appointments
    private lateinit var tvNoAppointments: TextView

    // Loading indicator
    private lateinit var progressAppointments: ProgressBar

    // List of appointments
    private val appointments = mutableListOf<Appointment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use our existing layout
        setContentView(R.layout.activity_my_appointments)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Connect UI elements
        recyclerAppointments =
            findViewById(R.id.recyclerAppointments)

        tvNoAppointments =
            findViewById(R.id.tvNoAppointments)

        progressAppointments =
            findViewById(R.id.progressAppointments)

        // Configure RecyclerView
        recyclerAppointments.layoutManager =
            LinearLayoutManager(this)

        recyclerAppointments.adapter =
            AppointmentAdapter(appointments)

        // Load appointments from Firestore
        loadAppointments()
    }

    /*
     * Loads appointments belonging to the
     * currently logged-in customer.
     */
    private fun loadAppointments() {

        // Get currently logged-in Firebase user
        val currentUser = auth.currentUser

        // Make sure the customer is logged in
        if (currentUser == null) {

            Toast.makeText(
                this,
                "Please login first",
                Toast.LENGTH_SHORT
            ).show()

            finish()

            return
        }

        // Show loading indicator
        progressAppointments.visibility = View.VISIBLE

        tvNoAppointments.visibility = View.GONE

        recyclerAppointments.visibility = View.GONE

        /*
         * Read the appointments collection.
         *
         * We filter using customerId so the customer
         * only sees their own appointments.
         */
        firestore.collection("appointments")
            .whereEqualTo(
                "customerId",
                currentUser.uid
            )
            .get()
            .addOnSuccessListener { documents ->

                // Remove old data
                appointments.clear()

                // Convert Firestore documents into Appointment objects
                for (document in documents) {

                    val appointment =
                        document.toObject(
                            Appointment::class.java
                        )

                    appointments.add(appointment)
                }

                // Hide loading indicator
                progressAppointments.visibility =
                    View.GONE

                /*
                 * Check whether appointments were found.
                 */
                if (appointments.isEmpty()) {

                    tvNoAppointments.visibility =
                        View.VISIBLE

                    recyclerAppointments.visibility =
                        View.GONE

                } else {

                    tvNoAppointments.visibility =
                        View.GONE

                    recyclerAppointments.visibility =
                        View.VISIBLE

                    /*
                     * Tell RecyclerView that the
                     * appointment list has changed.
                     */
                    recyclerAppointments.adapter =
                        AppointmentAdapter(appointments)
                }
            }
            .addOnFailureListener { exception ->

                // Hide loading indicator
                progressAppointments.visibility =
                    View.GONE

                // Show error message
                Toast.makeText(
                    this,
                    "Failed to load appointments: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}