package com.example.techfix

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

/*
 * ManageSparePartsActivity
 *
 * Management can:
 *
 * - View spare parts
 * - Add spare parts
 * - Edit spare parts
 * - Delete spare parts
 */

class ManageSparePartsActivity : AppCompatActivity() {

    // ==========================================
    // FIRESTORE
    // ==========================================

    private lateinit var firestore: FirebaseFirestore


    // ==========================================
    // RECYCLER VIEW
    // ==========================================

    private lateinit var recyclerView: RecyclerView


    // ==========================================
    // SPARE PART LIST
    // ==========================================

    private val sparePartList =
        mutableListOf<SparePart>()


    // ==========================================
    // ADAPTER
    // ==========================================

    private lateinit var adapter: SparePartAdapter


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_manage_spare_parts
        )

        // Initialize Firestore
        firestore =
            FirebaseFirestore.getInstance()


        // ==========================================
        // RECYCLER VIEW
        // ==========================================

        recyclerView =
            findViewById(
                R.id.rvSpareParts
            )

        recyclerView.layoutManager =
            LinearLayoutManager(this)


        // ==========================================
        // ADAPTER
        // ==========================================

        adapter = SparePartAdapter(

            sparePartList,

            // EDIT
            { sparePart ->

                showEditSparePartDialog(
                    sparePart
                )
            },

            // DELETE
            { sparePart ->

                confirmDeleteSparePart(
                    sparePart
                )
            }
        )

        recyclerView.adapter =
            adapter


        // ==========================================
        // ADD BUTTON
        // ==========================================

        findViewById<Button>(
            R.id.btnAddSparePart
        ).setOnClickListener {

            showAddSparePartDialog()
        }


        // ==========================================
        // LOAD DATA
        // ==========================================

        loadSpareParts()
    }


    // ==========================================
    // LOAD SPARE PARTS
    // ==========================================

    private fun loadSpareParts() {

        firestore
            .collection("spareParts")
            .get()

            .addOnSuccessListener { result ->

                sparePartList.clear()

                for (document in result.documents) {

                    val sparePart =
                        SparePart(

                            id = document.id,

                            name =
                                document.getString(
                                    "name"
                                ) ?: "",

                            category =
                                document.getString(
                                    "category"
                                ) ?: "",

                            quantity =
                                (
                                        document.getLong(
                                            "quantity"
                                        ) ?: 0L
                                        ).toInt(),

                            price =
                                document.getDouble(
                                    "price"
                                ) ?: 0.0
                        )

                    sparePartList.add(
                        sparePart
                    )
                }

                adapter.notifyDataSetChanged()
            }

            .addOnFailureListener { exception ->

                Toast.makeText(
                    this,
                    "Could not load spare parts: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    // ==========================================
    // ADD SPARE PART
    // ==========================================

    private fun showAddSparePartDialog() {

        val nameInput =
            EditText(this)

        nameInput.hint =
            "Spare part name"


        val categoryInput =
            EditText(this)

        categoryInput.hint =
            "Category"


        val quantityInput =
            EditText(this)

        quantityInput.hint =
            "Quantity"

        quantityInput.inputType =
            InputType.TYPE_CLASS_NUMBER


        val priceInput =
            EditText(this)

        priceInput.hint =
            "Price"

        priceInput.inputType =
            InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_DECIMAL


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
            categoryInput
        )

        container.addView(
            quantityInput
        )

        container.addView(
            priceInput
        )


        AlertDialog.Builder(this)

            .setTitle(
                "Add Spare Part"
            )

            .setView(container)

            .setPositiveButton(
                "ADD"
            ) { _, _ ->

                val name =
                    nameInput.text
                        .toString()
                        .trim()

                val category =
                    categoryInput.text
                        .toString()
                        .trim()

                val quantity =
                    quantityInput.text
                        .toString()
                        .trim()

                val price =
                    priceInput.text
                        .toString()
                        .trim()


                if (
                    name.isEmpty() ||
                    category.isEmpty() ||
                    quantity.isEmpty() ||
                    price.isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                val quantityValue =
                    quantity.toIntOrNull()

                val priceValue =
                    price.toDoubleOrNull()


                if (
                    quantityValue == null ||
                    priceValue == null
                ) {

                    Toast.makeText(
                        this,
                        "Enter valid quantity and price",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                val sparePart: Map<String, Any> =
                    hashMapOf(

                        "name" to name,

                        "category" to category,

                        "quantity" to quantityValue,

                        "price" to priceValue
                    )


                firestore
                    .collection("spareParts")
                    .add(sparePart)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Spare part added successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadSpareParts()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not add spare part: ${exception.message}",
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
    // EDIT SPARE PART
    // ==========================================

    private fun showEditSparePartDialog(
        sparePart: SparePart
    ) {

        val nameInput =
            EditText(this)

        nameInput.setText(
            sparePart.name
        )

        nameInput.hint =
            "Spare part name"


        val categoryInput =
            EditText(this)

        categoryInput.setText(
            sparePart.category
        )

        categoryInput.hint =
            "Category"


        val quantityInput =
            EditText(this)

        quantityInput.setText(
            sparePart.quantity.toString()
        )

        quantityInput.hint =
            "Quantity"

        quantityInput.inputType =
            InputType.TYPE_CLASS_NUMBER


        val priceInput =
            EditText(this)

        priceInput.setText(
            sparePart.price.toString()
        )

        priceInput.hint =
            "Price"

        priceInput.inputType =
            InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_DECIMAL


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
            categoryInput
        )

        container.addView(
            quantityInput
        )

        container.addView(
            priceInput
        )


        AlertDialog.Builder(this)

            .setTitle(
                "Edit Spare Part"
            )

            .setView(container)

            .setPositiveButton(
                "SAVE"
            ) { _, _ ->

                val name =
                    nameInput.text
                        .toString()
                        .trim()

                val category =
                    categoryInput.text
                        .toString()
                        .trim()

                val quantity =
                    quantityInput.text
                        .toString()
                        .trim()

                val price =
                    priceInput.text
                        .toString()
                        .trim()


                if (
                    name.isEmpty() ||
                    category.isEmpty() ||
                    quantity.isEmpty() ||
                    price.isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                val quantityValue =
                    quantity.toIntOrNull()

                val priceValue =
                    price.toDoubleOrNull()


                if (
                    quantityValue == null ||
                    priceValue == null
                ) {

                    Toast.makeText(
                        this,
                        "Enter valid quantity and price",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }


                val updatedData: Map<String, Any> =
                    hashMapOf(

                        "name" to name,

                        "category" to category,

                        "quantity" to
                                quantityValue,

                        "price" to
                                priceValue
                    )


                firestore
                    .collection("spareParts")
                    .document(sparePart.id)
                    .update(updatedData)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Spare part updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadSpareParts()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not update spare part: ${exception.message}",
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
    // DELETE SPARE PART
    // ==========================================

    private fun confirmDeleteSparePart(
        sparePart: SparePart
    ) {

        AlertDialog.Builder(this)

            .setTitle(
                "Delete Spare Part"
            )

            .setMessage(
                "Are you sure you want to delete ${sparePart.name}?"
            )

            .setPositiveButton(
                "DELETE"
            ) { _, _ ->

                firestore
                    .collection("spareParts")
                    .document(sparePart.id)
                    .delete()

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Spare part deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadSpareParts()
                    }

                    .addOnFailureListener { exception ->

                        Toast.makeText(
                            this,
                            "Could not delete spare part: ${exception.message}",
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