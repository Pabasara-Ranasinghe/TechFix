package com.example.techfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            // Firebase registration will be implemented later.
            val intent = Intent(this, CustomerHomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}