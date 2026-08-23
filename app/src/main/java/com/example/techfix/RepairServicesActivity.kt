package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RepairServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_repair_services)

        val btnComputerRepair =
            findViewById<Button>(R.id.btnComputerRepair)

        val btnMobileRepair =
            findViewById<Button>(R.id.btnMobileRepair)

        // Computer Repair
        btnComputerRepair.setOnClickListener {

            val intent = Intent(
                this,
                BookRepairActivity::class.java
            )

            intent.putExtra(
                "DEVICE_TYPE",
                "Computer"
            )

            startActivity(intent)
        }

        // Mobile Phone Repair
        btnMobileRepair.setOnClickListener {

            val intent = Intent(
                this,
                BookRepairActivity::class.java
            )

            intent.putExtra(
                "DEVICE_TYPE",
                "Mobile Phone"
            )

            startActivity(intent)
        }
    }
}