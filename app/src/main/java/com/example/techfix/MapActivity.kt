package com.example.techfix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        // ==========================================
        // COLOMBO BRANCH
        // ==========================================

        val btnColomboMap =
            findViewById<Button>(R.id.btnColomboMap)

        btnColomboMap.setOnClickListener {

            openMap(
                "TechFix Colombo, Sri Lanka"
            )
        }


        // ==========================================
        // GALLE BRANCH
        // ==========================================

        val btnGalleMap =
            findViewById<Button>(R.id.btnGalleMap)

        btnGalleMap.setOnClickListener {

            openMap(
                "TechFix Galle, Sri Lanka"
            )
        }
    }


    // ==========================================
    // OPEN GOOGLE MAPS
    // ==========================================

    private fun openMap(location: String) {

        val uri =
            Uri.parse(
                "geo:0,0?q=${Uri.encode(location)}"
            )

        val intent =
            Intent(
                Intent.ACTION_VIEW,
                uri
            )

        intent.setPackage(
            "com.google.android.apps.maps"
        )

        try {

            startActivity(intent)

        } catch (exception: Exception) {

            // Google Maps is not installed.
            // Open the location using a web browser.

            val webUri =
                Uri.parse(
                    "https://www.google.com/maps/search/?api=1&query=${Uri.encode(location)}"
                )

            val webIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    webUri
                )

            try {

                startActivity(webIntent)

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Could not open map",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}