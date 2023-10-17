package com.example.navian

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Switch
import com.example.navian.UnitConverter.Companion.kilometersToMiles

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val metricSwitch = findViewById<Switch>(R.id.switch1)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        metricSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("isMetric", isChecked)
            editor.apply()
        }
        val isMetric = sharedPreferences.getBoolean("isMetric", true)

        fun convertDistance(distance: Double, isMetric: Boolean): Double {
            return if (isMetric) {
                distance // No conversion needed for metric
            } else {
                kilometersToMiles(distance)
            }
        }
        val distanceSeekBar = findViewById<SeekBar>(R.id.seekBar)
        distanceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Save the progress as the maximum distance
                val maxDistanceInKilometers = progress.toDouble()
                val convertedMaxDistance = convertDistance(maxDistanceInKilometers, isMetric)

                editor.putFloat("maxDistance", convertedMaxDistance.toFloat())
                editor.apply()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        val DEFAULT_MAX_DISTANCE_KM = 10.0
        val maxDistanceInKilometers = sharedPreferences.getFloat("maxDistance", DEFAULT_MAX_DISTANCE_KM.toFloat())

        val maxDistance = convertDistance(maxDistanceInKilometers.toDouble(), isMetric)



    }
}