package com.example.techfix

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

/*
 * ManageTechniciansActivity
 *
 * Management can:
 *
 * 1. View technicians
 * 2. Add technicians
 * 3. Edit technicians
 * 4. Delete technicians
 */

class ManageTechniciansActivity : AppCompatActivity() {

    // ==========================================
    // FIRESTORE
    // ==========================================

    private lateinit var firestore: FirebaseFirestore

    // ==========================================
    // RECYCLER VIEW
    // ==========================================

    private lateinit var recyclerView: RecyclerView

    // ==========================================
    // TECHNICIAN LIST
    // ==========================================

    private val technicianList =
        mutableListOf<Technician>()

    // ==========================================
    // ADAPTER
    // ==========================================

    private lateinit var adapter: TechnicianAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load layout
        setContentView(
            R.layout.activity_manage_technicians
        )

        // Initialize Firestore
        firestore =
            FirebaseFirestore.getInstance()

        // ==========================================
        // CONNECT RECYCLER VIEW
        // ==========================================

        recyclerView =
            findViewById(
                R.id.rvTechnicians
            )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        // ==========================================
        // CREATE ADAPTER
        // ==========================================

        adapter = TechnicianAdapter(

            technicianList,

            // EDIT
            { technician ->
                showEditTechnicianDialog(
                    technician
                )
            },

            // DELETE
            { technician ->
                confirmDeleteTechnician(
                    technician
                )
            }
        )

        recyclerView.adapter =
            adapter

        // ==========================================
        // ADD TECHNICIAN BUTTON
        // ==========================================

        findViewById<Button>(
            R.id.btnAddTechnician
        ).setOnClickListener {

            showAddTechnicianDialog()
        }

        // ==========================================
        // LOAD TECHNICIANS
        // ==========================================

        loadTechnicians()
    }


    // ==========================================
    // LOAD TECHNICIANS
    // ==========================================

    private fun loadTechnicians() {

        firestore
            .collection("technicians")
            .get()

            .addOnSuccessListener { result ->

                technicianList.clear()

                for (document in result.documents) {

                    val technician =
                        Technician(

                            id = document.id,

                            name =
                                document.getString(
                                    "name"
                                ) ?: "",

                            specialization =
                                document.getString(
                                    "specialization"
                                ) ?: "",

                            phone =
                                document.getString(
                                    "phone"
                                ) ?: "",

                            availability =
                                document.getString(
                                    "availability"
                                ) ?: "Available"
                        )

                    technicianList.add(
                        technician
                    )
                }

                adapter.notifyDataSetChanged()
            }

            .addOnFailureListener { exception ->

                Toast.makeText(
                    this,
                    "Could not load technicians: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    // ==========================================
    // ADD TECHNICIAN
    // ==========================================

    private fun showAddTechnicianDialog() {

        // Name
        val nameInput =
            EditText(this)

        nameInput.hint =
            "Technician name"


        // Specialization
        val specializationInput =
            EditText(this)

        specializationInput.hint =
            "Specialization"


        // Phone
        val phoneInput =
            EditText(this)

        phoneInput.hint =
            "Phone number"


        // Container
        val container =
            LinearLayout(this)

        container.orientation =
            LinearLayout.VERTICAL

        container.setPadding(
            40,
            10,
            40,
            10
        )

        container.addView(
            nameInput
        )

        container.addView(
            specializationInput
        )

        container.addView(
            phoneInput
        )


        // Dialog
        AlertDialog.Builder(this)

            .setTitle(
                "Add Technician"
            )

            .setView(container)

            .setPositiveButton(
                "ADD"
            ) { _, _ ->

                val name =
                    nameInput.text
                        .toString()
                        .trim()

                val specialization =
                    specializationInput.text
                        .toString()
                        .trim()

                val phone =
                    phoneInput.text
                        .toString()
                        .trim()


                // Validate
                if (
                    name.isEmpty() ||
                    specialization.isEmpty() ||
                    phone.isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                // Technician data
                val technician: Map<String, Any> =
                    hashMapOf(

                        "name" to name,

                        "specialization" to
                                specialization,

                        "phone" to phone,

                        "availability" to
                                "Available"
                    )


                // Save to Firestore
                firestore
                    .collection("technicians")
                    .add(technician)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Technician added successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadTechnicians()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not add technician: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }

            .setNegativeButton(
                "CANCEL",
                null
            )

            .show()
    }


    // ==========================================
    // EDIT TECHNICIAN
    // ==========================================

    private fun showEditTechnicianDialog(
        technician: Technician
    ) {

        // Name
        val nameInput =
            EditText(this)

        nameInput.setText(
            technician.name
        )

        nameInput.hint =
            "Technician name"


        // Specialization
        val specializationInput =
            EditText(this)

        specializationInput.setText(
            technician.specialization
        )

        specializationInput.hint =
            "Specialization"


        // Phone
        val phoneInput =
            EditText(this)

        phoneInput.setText(
            technician.phone
        )

        phoneInput.hint =
            "Phone number"


        // Availability
        val availabilityInput =
            EditText(this)

        availabilityInput.setText(
            technician.availability
        )

        availabilityInput.hint =
            "Availability"


        // Container
        val container =
            LinearLayout(this)

        container.orientation =
            LinearLayout.VERTICAL

        container.setPadding(
            40,
            10,
            40,
            10
        )

        container.addView(
            nameInput
        )

        container.addView(
            specializationInput
        )

        container.addView(
            phoneInput
        )

        container.addView(
            availabilityInput
        )


        // Dialog
        AlertDialog.Builder(this)

            .setTitle(
                "Edit Technician"
            )

            .setView(container)

            .setPositiveButton(
                "SAVE"
            ) { _, _ ->

                val name =
                    nameInput.text
                        .toString()
                        .trim()

                val specialization =
                    specializationInput.text
                        .toString()
                        .trim()

                val phone =
                    phoneInput.text
                        .toString()
                        .trim()

                val availability =
                    availabilityInput.text
                        .toString()
                        .trim()


                // Validate
                if (
                    name.isEmpty() ||
                    specialization.isEmpty() ||
                    phone.isEmpty() ||
                    availability.isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                // Updated technician data
                val updatedData: Map<String, Any> =
                    hashMapOf(

                        "name" to name,

                        "specialization" to
                                specialization,

                        "phone" to phone,

                        "availability" to
                                availability
                    )


                // Update Firestore document
                firestore
                    .collection("technicians")
                    .document(technician.id)
                    .update(updatedData)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Technician updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadTechnicians()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not update technician: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }

            .setNegativeButton(
                "CANCEL",
                null
            )

            .show()
    }


    // ==========================================
    // DELETE TECHNICIAN
    // ==========================================

    private fun confirmDeleteTechnician(
        technician: Technician
    ) {

        AlertDialog.Builder(this)

            .setTitle(
                "Delete Technician"
            )

            .setMessage(
                "Are you sure you want to delete ${technician.name}?"
            )

            .setPositiveButton(
                "DELETE"
            ) { _, _ ->

                firestore
                    .collection("technicians")
                    .document(technician.id)
                    .delete()

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Technician deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadTechnicians()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not delete technician: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }

            .setNegativeButton(
                "CANCEL",
                null
            )

            .show()
    }
}