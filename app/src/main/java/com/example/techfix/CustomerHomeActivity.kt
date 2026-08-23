package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CustomerHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_customer_home)


        // ==========================================
        // HERO - BOOK A REPAIR
        // ==========================================

        val btnHeroBookRepair =
            findViewById<Button>(R.id.btnHeroBookRepair)

        btnHeroBookRepair.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // LAPTOP DIAGNOSTICS - BOOK NOW
        // ==========================================

        val btnLaptopBookNow =
            findViewById<Button>(R.id.btnLaptopBookNow)

        btnLaptopBookNow.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            intent.putExtra(
                "DEVICE_TYPE",
                "Laptop"
            )

            startActivity(intent)
        }


        // ==========================================
        // MOBILE SCREEN REPAIR - BOOK NOW
        // ==========================================

        val btnMobileBookNow =
            findViewById<Button>(R.id.btnMobileBookNow)

        btnMobileBookNow.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            intent.putExtra(
                "DEVICE_TYPE",
                "Mobile Phone"
            )

            startActivity(intent)
        }


        // ==========================================
        // CATEGORY - ALL
        // ==========================================

        val btnCategoryAll =
            findViewById<Button>(R.id.btnCategoryAll)

        btnCategoryAll.setOnClickListener {

            // All services are already displayed.
        }


        // ==========================================
        // CATEGORY - PHONES
        // ==========================================

        val btnCategoryPhones =
            findViewById<Button>(R.id.btnCategoryPhones)

        btnCategoryPhones.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            intent.putExtra(
                "DEVICE_TYPE",
                "Mobile Phone"
            )

            startActivity(intent)
        }


        // ==========================================
        // CATEGORY - PC / MAC
        // ==========================================

        val btnCategoryPC =
            findViewById<Button>(R.id.btnCategoryPC)

        btnCategoryPC.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            intent.putExtra(
                "DEVICE_TYPE",
                "Laptop"
            )

            startActivity(intent)
        }


        // ==========================================
        // CATEGORY - SCREENS
        // ==========================================

        val btnCategoryScreens =
            findViewById<Button>(R.id.btnCategoryScreens)

        btnCategoryScreens.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // CATEGORY - BATTERY
        // ==========================================

        val btnCategoryBattery =
            findViewById<Button>(R.id.btnCategoryBattery)

        btnCategoryBattery.setOnClickListener {

            val intent =
                Intent(
                    this,
                    BookRepairActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // BOTTOM NAVIGATION - HOME
        // ==========================================

        val btnNavHome =
            findViewById<Button>(R.id.btnNavHome)

        btnNavHome.setOnClickListener {

            // Already on the home page.
        }


        // ==========================================
        // BOTTOM NAVIGATION - BOOKINGS
        // ==========================================

        val btnNavBookings =
            findViewById<Button>(R.id.btnNavBookings)

        btnNavBookings.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MyBookingsActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // BOTTOM NAVIGATION - MAP
        // ==========================================

        val btnNavMap =
            findViewById<Button>(R.id.btnNavMap)

        btnNavMap.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MapActivity::class.java
                )

            startActivity(intent)
        }


        // ==========================================
        // BOTTOM NAVIGATION - PROFILE
        // ==========================================

        val btnNavProfile =
            findViewById<Button>(R.id.btnNavProfile)

        btnNavProfile.setOnClickListener {

            // Profile will be connected next.
        }
    }
}