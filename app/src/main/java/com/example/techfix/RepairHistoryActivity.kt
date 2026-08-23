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
 * RepairHistoryActivity
 *
 * This screen displays the customer's completed repairs.
 *
 * Data is loaded from the Firestore "appointments"
 * collection.
 *
 * Only appointments with:
 *
 *     status = "Completed"
 *
 * are displayed here.
 */

class RepairHistoryActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Firebase Firestore
    private lateinit var firestore: FirebaseFirestore

    // RecyclerView used to display repair history
    private lateinit var recyclerHistory: RecyclerView

    // Message displayed when there is no history
    private lateinit var tvNoHistory: TextView

    // Loading indicator
    private lateinit var progressHistory: ProgressBar

    // List containing completed repairs
    private val historyList =
        mutableListOf<RepairHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * Use the layout we created earlier.
         */
        setContentView(
            R.layout.activity_repair_history
        )

        // ==========================================
        // INITIALIZE FIREBASE
        // ==========================================

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        // ==========================================
        // CONNECT UI ELEMENTS
        // ==========================================

        recyclerHistory =
            findViewById(R.id.recyclerHistory)

        tvNoHistory =
            findViewById(R.id.tvNoHistory)

        progressHistory =
            findViewById(R.id.progressHistory)

        // ==========================================
        // CONFIGURE RECYCLER VIEW
        // ==========================================

        recyclerHistory.layoutManager =
            LinearLayoutManager(this)

        recyclerHistory.adapter =
            RepairHistoryAdapter(historyList)

        // ==========================================
        // LOAD REPAIR HISTORY
        // ==========================================

        loadRepairHistory()
    }

    /*
     * Loads completed repairs from Firestore.
     */
    private fun loadRepairHistory() {

        // Get currently logged-in customer
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
        progressHistory.visibility =
            View.VISIBLE

        // Hide empty message
        tvNoHistory.visibility =
            View.GONE

        // Hide RecyclerView while loading
        recyclerHistory.visibility =
            View.GONE

        /*
         * Get appointments belonging to the
         * currently logged-in customer.
         *
         * We also filter for completed repairs.
         */
        firestore.collection("appointments")
            .whereEqualTo(
                "customerId",
                currentUser.uid
            )
            .whereEqualTo(
                "status",
                "Completed"
            )
            .get()
            .addOnSuccessListener { documents ->

                // Remove old data
                historyList.clear()

                /*
                 * Convert every Firestore document
                 * into a RepairHistory object.
                 */
                for (document in documents) {

                    val repair =
                        document.toObject(
                            RepairHistory::class.java
                        )

                    historyList.add(repair)
                }

                // Hide loading indicator
                progressHistory.visibility =
                    View.GONE

                // ==================================
                // CHECK WHETHER HISTORY EXISTS
                // ==================================

                if (historyList.isEmpty()) {

                    /*
                     * No completed repairs found.
                     */
                    tvNoHistory.visibility =
                        View.VISIBLE

                    recyclerHistory.visibility =
                        View.GONE

                } else {

                    /*
                     * Completed repairs were found.
                     */
                    tvNoHistory.visibility =
                        View.GONE

                    recyclerHistory.visibility =
                        View.VISIBLE

                    /*
                     * Refresh the RecyclerView.
                     */
                    recyclerHistory.adapter =
                        RepairHistoryAdapter(historyList)
                }
            }
            .addOnFailureListener { exception ->

                // Hide loading indicator
                progressHistory.visibility =
                    View.GONE

                /*
                 * Show the Firestore error.
                 */
                Toast.makeText(
                    this,
                    "Failed to load repair history: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}