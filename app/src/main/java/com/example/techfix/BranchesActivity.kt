package com.example.techfix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
 * BranchesActivity
 *
 * Displays TechFix branch information.
 *
 * The customer can select a branch and open
 * its location using Google Maps.
 */

class BranchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the Branches layout
        setContentView(R.layout.activity_branches)

        // ==========================================
        // CONNECT MAP BUTTONS
        // ==========================================

        val btnColomboMap =
            findViewById<Button>(R.id.btnColomboMap)

        val btnGalleMap =
            findViewById<Button>(R.id.btnGalleMap)

        // ==========================================
        // COLOMBO BRANCH
        // ==========================================

        btnColomboMap.setOnClickListener {

            /*
             * Open the Colombo branch location
             * in Google Maps.
             *
             * This search URL allows Google Maps
             * to find the location.
             */
            openGoogleMaps(
                "TechFix Colombo Sri Lanka"
            )
        }

        // ==========================================
        // GALLE BRANCH
        // ==========================================

        btnGalleMap.setOnClickListener {

            /*
             * Open the Galle branch location
             * in Google Maps.
             */
            openGoogleMaps(
                "TechFix Galle Sri Lanka"
            )
        }
    }

    /*
     * Opens Google Maps using an Android Intent.
     */
    private fun openGoogleMaps(location: String) {

        // Create a Google Maps search URI
        val uri = Uri.parse(
            "geo:0,0?q=${Uri.encode(location)}"
        )

        // Create the Maps Intent
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri
        )

        /*
         * Try to open the location.
         */
        try {

            startActivity(intent)

        } catch (exception: Exception) {

            /*
             * If no Maps application is available,
             * show a message to the customer.
             */
            Toast.makeText(
                this,
                "Google Maps is not available on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}