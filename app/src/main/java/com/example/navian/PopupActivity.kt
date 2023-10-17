package com.example.navian
// TrayPopupActivity.kt
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)
    }

    // Handle button clicks here
    fun onButton1Click(view: View) {
        // Handle the first button click
        val observationButton = findViewById<View>(R.id.observation_button)
        // Handle the other buttons here
        observationButton.setOnClickListener {
            // Open the small screen activity here
            val intent = Intent(this, ObservationActivity::class.java)
            startActivity(intent)
        }

    }

    fun onButton2Click(view: View) {
        // Handle the second button click
        val sessionButton = findViewById<View>(R.id.session_button)
        // Handle the other buttons here
        sessionButton.setOnClickListener {
            // Open the small screen activity here
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}
