package com.example.navian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val observationsButton = findViewById<Button>(R.id.observations_button)
        val backButton = findViewById<Button>(R.id.back_button)
        val settingsButton = findViewById<Button>(R.id.settings_button )

        // Navigate to the ObservationActivity
        observationsButton.setOnClickListener {
            val intent = Intent(this, ObservationActivity::class.java)
            startActivity(intent)
        }

        // Navigate back to the LoginActivity
        backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navigate to the SettingsActivity
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}