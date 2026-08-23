package com.example.techfix

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

/*
 * BookRepairActivity
 *
 * Allows customers to:
 *
 * 1. Select a device type
 * 2. Enter the device model
 * 3. Describe the repair problem
 * 4. Select a preferred repair date
 * 5. Select a device image
 * 6. Submit a repair appointment
 *
 * Appointment information is stored in Firestore.
 *
 * The selected image is currently displayed locally.
 * Firebase Storage is NOT required for this step.
 */

class BookRepairActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Firebase Firestore
    private lateinit var firestore: FirebaseFirestore

    /*
     * Stores the URI of the image selected from
     * the customer's phone.
     */
    private var selectedImageUri: Uri? = null

    /*
     * Android Photo Picker.
     *
     * This allows the customer to select an image
     * from the device gallery.
     */
    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {

                /*
                 * Save the selected image URI.
                 */
                selectedImageUri = uri

                /*
                 * Display the selected image
                 * in the ImageView.
                 */
                findViewById<ImageView>(
                    R.id.ivDeviceImage
                ).setImageURI(uri)

                Toast.makeText(
                    this,
                    "Device image selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the booking screen
        setContentView(R.layout.activity_book_repair)

        // ==========================================
        // INITIALIZE FIREBASE
        // ==========================================

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        // ==========================================
        // CONNECT UI ELEMENTS
        // ==========================================

        val spinnerDeviceType =
            findViewById<Spinner>(
                R.id.spinnerDeviceType
            )

        val etDeviceModel =
            findViewById<EditText>(
                R.id.etDeviceModel
            )

        val etProblem =
            findViewById<EditText>(
                R.id.etProblem
            )

        val etDate =
            findViewById<EditText>(
                R.id.etDate
            )

        val btnSelectImage =
            findViewById<Button>(
                R.id.btnSelectImage
            )

        val btnBookRepair =
            findViewById<Button>(
                R.id.btnBookRepair
            )

        // ==========================================
        // DEVICE TYPES
        // ==========================================

        val deviceTypes = arrayOf(
            "Select device type",
            "Computer",
            "Laptop",
            "Mobile Phone",
            "Tablet"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            deviceTypes
        )

        spinnerDeviceType.adapter = adapter

        // ==========================================
        // DEVICE TYPE FROM REPAIR SERVICES
        // ==========================================

        /*
         * If the customer selected a device type
         * from the Repair Services screen, automatically
         * select it here.
         */
        val selectedDeviceType =
            intent.getStringExtra("DEVICE_TYPE")

        if (selectedDeviceType != null) {

            val position =
                deviceTypes.indexOf(
                    selectedDeviceType
                )

            if (position >= 0) {

                spinnerDeviceType.setSelection(
                    position
                )
            }
        }

        // ==========================================
        // DATE PICKER
        // ==========================================

        etDate.setOnClickListener {

            val calendar =
                Calendar.getInstance()

            val year =
                calendar.get(Calendar.YEAR)

            val month =
                calendar.get(Calendar.MONTH)

            val day =
                calendar.get(Calendar.DAY_OF_MONTH)

            /*
             * Create date picker dialog.
             */
            val datePicker =
                DatePickerDialog(
                    this,

                    { _, selectedYear,
                      selectedMonth,
                      selectedDay ->

                        /*
                         * Format selected date.
                         */
                        val formattedDate =
                            "$selectedDay/${selectedMonth + 1}/$selectedYear"

                        /*
                         * Display date in EditText.
                         */
                        etDate.setText(
                            formattedDate
                        )
                    },

                    year,
                    month,
                    day
                )

            datePicker.show()
        }

        // ==========================================
        // SELECT DEVICE IMAGE
        // ==========================================

        btnSelectImage.setOnClickListener {

            /*
             * Open the phone gallery/photo picker.
             */
            imagePicker.launch(
                "image/*"
            )
        }

        // ==========================================
        // BOOK REPAIR
        // ==========================================

        btnBookRepair.setOnClickListener {

            // Get selected device type
            val deviceType =
                spinnerDeviceType
                    .selectedItem
                    .toString()

            // Get device model
            val deviceModel =
                etDeviceModel.text
                    .toString()
                    .trim()

            // Get problem description
            val problem =
                etProblem.text
                    .toString()
                    .trim()

            // Get preferred date
            val date =
                etDate.text
                    .toString()
                    .trim()

            // ======================================
            // VALIDATION
            // ======================================

            if (deviceType ==
                "Select device type"
            ) {

                Toast.makeText(
                    this,
                    "Please select a device type",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (deviceModel.isEmpty()) {

                etDeviceModel.error =
                    "Please enter the device model"

                etDeviceModel.requestFocus()

                return@setOnClickListener
            }

            if (problem.isEmpty()) {

                etProblem.error =
                    "Please describe the problem"

                etProblem.requestFocus()

                return@setOnClickListener
            }

            if (date.isEmpty()) {

                etDate.error =
                    "Please select a preferred date"

                etDate.requestFocus()

                return@setOnClickListener
            }

            // ======================================
            // GET CURRENT USER
            // ======================================

            val currentUser =
                auth.currentUser

            if (currentUser == null) {

                Toast.makeText(
                    this,
                    "Please login first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // ======================================
            // DISABLE BUTTON WHILE SAVING
            // ======================================

            btnBookRepair.isEnabled =
                false

            btnBookRepair.text =
                "BOOKING..."

            // ======================================
            // APPOINTMENT DATA
            // ======================================

            /*
             * The image URI is intentionally not stored
             * in Firestore yet.
             *
             * We will handle permanent image storage
             * separately.
             */
            val appointment =
                hashMapOf(

                    "customerId" to
                            currentUser.uid,

                    "deviceType" to
                            deviceType,

                    "deviceModel" to
                            deviceModel,

                    "problem" to
                            problem,

                    "preferredDate" to
                            date,

                    "status" to
                            "Pending",

                    "createdAt" to
                            System.currentTimeMillis()
                )

            // ======================================
            // SAVE APPOINTMENT TO FIRESTORE
            // ======================================

            firestore
                .collection("appointments")
                .add(appointment)

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Repair appointment booked successfully!",
                        Toast.LENGTH_LONG
                    ).show()

                    /*
                     * Return to the previous screen
                     * after successful booking.
                     */
                    finish()
                }

                .addOnFailureListener { exception ->

                    /*
                     * Re-enable the button if saving fails.
                     */
                    btnBookRepair.isEnabled =
                        true

                    btnBookRepair.text =
                        "BOOK REPAIR"

                    Toast.makeText(
                        this,
                        "Booking failed: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}