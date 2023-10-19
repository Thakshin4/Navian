package com.example.navian

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val menuButton = findViewById<View>(R.id.menu_button)

        // Set a click listener for the menu button
        menuButton.setOnClickListener { view ->

            val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenu)
            val popup = PopupMenu(wrapper, view)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_option1 -> {
//                        // Handle "Option 1" (Home) click
//                        val intent = Intent(this, HomeActivity::class.java)
//                        startActivity(intent)
                        true
                    }
                    R.id.menu_option2 -> {
                        // Handle "Option 2" (View Observations) click
                        val viewObservationsIntent = Intent(this, ViewObservationsActivity::class.java)
                        startActivity(viewObservationsIntent)
                        true
                    }
                    R.id.menu_option3 -> {
                        // Handle "Option 3" (Add Observation) click
                        val addObservationIntent = Intent(this, ObservationActivity::class.java)
                        startActivity(addObservationIntent)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }

        val buttonTray = findViewById<View>(R.id.tray_button)
        // Handle the other buttons here
        buttonTray.setOnClickListener {
            // Open the small screen activity here
            val intent = Intent(this, PopupActivity::class.java)
            startActivity(intent)
        }

        val buttonSettings = findViewById<View>(R.id.settings_button)
        buttonSettings.setOnClickListener {
            // Open the settings activity
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }
}
