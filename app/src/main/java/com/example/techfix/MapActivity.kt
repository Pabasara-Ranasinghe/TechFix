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

            // Colombo, Sri Lanka
            openMap(
                6.9271,
                79.8612,
                "TechFix Colombo"
            )
        }


        // ==========================================
        // GALLE BRANCH
        // ==========================================

        val btnGalleMap =
            findViewById<Button>(R.id.btnGalleMap)

        btnGalleMap.setOnClickListener {

            // Galle, Sri Lanka
            openMap(
                6.0329,
                80.2168,
                "TechFix Galle"
            )
        }
    }


    // ==========================================
    // OPEN GOOGLE MAPS
    // ==========================================

    private fun openMap(
        latitude: Double,
        longitude: Double,
        label: String
    ) {

        val uri = Uri.parse(
            "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
        )

        val intent = Intent(
            Intent.ACTION_VIEW,
            uri
        )

        intent.setPackage(
            "com.google.android.apps.maps"
        )

        try {

            startActivity(intent)

        } catch (exception: Exception) {

            // ==========================================
            // FALLBACK TO WEB GOOGLE MAPS
            // ==========================================

            val webUri = Uri.parse(
                "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
            )

            val webIntent = Intent(
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
