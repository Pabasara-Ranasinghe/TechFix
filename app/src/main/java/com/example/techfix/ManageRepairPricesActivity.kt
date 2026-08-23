package com.example.techfix

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManageRepairPricesActivity : AppCompatActivity() {

    private lateinit var rvRepairPrices: RecyclerView
    private lateinit var btnAddRepairPrice: Button
    private lateinit var btnBackToManagement: Button

    private lateinit var adapter: RepairPriceAdapter

    private val repairPrices =
        mutableListOf<RepairPrice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_manage_repair_prices
        )

        rvRepairPrices =
            findViewById(R.id.rvRepairPrices)

        btnAddRepairPrice =
            findViewById(R.id.btnAddRepairPrice)

        btnBackToManagement =
            findViewById(R.id.btnBackToManagement)

        adapter =
            RepairPriceAdapter(
                repairPrices
            )

        rvRepairPrices.layoutManager =
            LinearLayoutManager(this)

        rvRepairPrices.adapter =
            adapter

        addSamplePrices()

        btnAddRepairPrice.setOnClickListener {

            showAddRepairPriceDialog()
        }

        btnBackToManagement.setOnClickListener {

            finish()
        }
    }

    private fun addSamplePrices() {

        if (repairPrices.isNotEmpty()) {
            return
        }

        repairPrices.add(
            RepairPrice(
                id = "1",
                serviceName = "Laptop Diagnostics",
                deviceType = "PC / MAC",
                description = "Full diagnostic and repair",
                price = 3000.0
            )
        )

        repairPrices.add(
            RepairPrice(
                id = "2",
                serviceName = "Mobile Screen Repair",
                deviceType = "Mobile Phone",
                description = "Professional screen replacement",
                price = 5000.0
            )
        )

        adapter.notifyItemRangeInserted(
            0,
            repairPrices.size
        )
    }

    private fun showAddRepairPriceDialog() {

        val dialogView =
            LayoutInflater.from(this)
                .inflate(
                    R.layout.dialog_add_repair_price,
                    null
                )

        val etServiceName =
            dialogView.findViewById<EditText>(
                R.id.etServiceName
            )

        val etDeviceType =
            dialogView.findViewById<EditText>(
                R.id.etDeviceType
            )

        val etDescription =
            dialogView.findViewById<EditText>(
                R.id.etDescription
            )

        val etPrice =
            dialogView.findViewById<EditText>(
                R.id.etPrice
            )

        val dialog =
            AlertDialog.Builder(this)
                .setTitle("Add Repair Price")
                .setView(dialogView)
                .setNegativeButton(
                    "CANCEL",
                    null
                )
                .setPositiveButton(
                    "ADD",
                    null
                )
                .create()

        dialog.setOnShowListener {

            dialog.getButton(
                AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener {

                val serviceName =
                    etServiceName.text
                        .toString()
                        .trim()

                val deviceType =
                    etDeviceType.text
                        .toString()
                        .trim()

                val description =
                    etDescription.text
                        .toString()
                        .trim()

                val priceText =
                    etPrice.text
                        .toString()
                        .trim()

                if (
                    serviceName.isEmpty() ||
                    deviceType.isEmpty() ||
                    description.isEmpty() ||
                    priceText.isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                val price =
                    priceText.toDoubleOrNull()

                if (price == null) {

                    Toast.makeText(
                        this,
                        "Enter a valid price",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                val newRepairPrice =
                    RepairPrice(
                        id =
                            System.currentTimeMillis()
                                .toString(),

                        serviceName =
                            serviceName,

                        deviceType =
                            deviceType,

                        description =
                            description,

                        price =
                            price
                    )

                adapter.addRepairPrice(
                    newRepairPrice
                )

                rvRepairPrices.scrollToPosition(
                    repairPrices.size - 1
                )

                Toast.makeText(
                    this,
                    "Repair price added",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.dismiss()
            }
        }

        dialog.show()
    }
}