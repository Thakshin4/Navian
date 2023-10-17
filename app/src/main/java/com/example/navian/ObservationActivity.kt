package com.example.navian
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class ObservationActivity : AppCompatActivity() {

    // Create variables for UI elements
    private lateinit var speciesEditText: EditText
    private lateinit var dateTimeEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var capturePhotoButton: Button
    private lateinit var notesEditText: EditText
    private lateinit var saveButton: Button

    // Create a list to store bird observations
    private val birdObservations = mutableListOf<BirdObservation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observation)

        // Initialize UI element variables
        speciesEditText = findViewById(R.id.species_edittext)
        dateTimeEditText = findViewById(R.id.date_time_edittext)
        locationEditText = findViewById(R.id.location_edittext)
        capturePhotoButton = findViewById(R.id.capture_photo_button)
        notesEditText = findViewById(R.id.notes_edittext)
        saveButton = findViewById(R.id.save_button)

        // Handle the Save Observation button click
        saveButton.setOnClickListener {
            // Get user input from EditText fields
            val species = speciesEditText.text.toString()
            val dateTime = dateTimeEditText.text.toString()
            val location = locationEditText.text.toString()
            val notes = notesEditText.text.toString()

            // Check if the input fields are not empty
            if (species.isNotEmpty() && dateTime.isNotEmpty() && location.isNotEmpty()) {
                // Create a BirdObservation object
                val observation = BirdObservation(species, dateTime, location, notes)

                // Add the observation to the list
                birdObservations.add(observation)

                // Optionally, you can clear the input fields for the next entry
                speciesEditText.text.clear()
                dateTimeEditText.text.clear()
                locationEditText.text.clear()
                notesEditText.text.clear()

                // Optionally, show a message to the user that the observation was saved
                // (e.g., using a Toast message).
            } else {
                // Handle validation errors (e.g., show an error message to the user).
            }
        }
    }
}
