package com.example.navian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        // Find the buttons
        val observationButton = findViewById<View>(R.id.observation_button)
        val sessionButton = findViewById<View>(R.id.session_button)

        // Set click listeners for the buttons
        observationButton.setOnClickListener {
            openObservationActivity()
        }

        sessionButton.setOnClickListener {
            openMapActivity()
        }
    }

    // Define functions to open activities
    private fun openObservationActivity() {
        val intent = Intent(this, ObservationActivity::class.java)
        startActivity(intent)
    }

    private fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}
