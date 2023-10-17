package com.example.navian

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.util.Calendar

// Create a list to store bird observations
val birdObservations = mutableListOf<BirdObservation>()

class ObservationActivity : AppCompatActivity() {

    // Create variables for UI elements
    private lateinit var speciesEditText: EditText
    private lateinit var dateTimeEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var capturePhotoButton: Button
    private lateinit var notesEditText: EditText
    private lateinit var saveButton: Button

    // Variable to store the captured image URI
    private var capturedImageUri: Uri? = null

    // Request code for image capture permission
    private val CAMERA_PERMISSION_REQUEST_CODE = 101

    // Activity result launcher for capturing an image
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

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

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        val chooseDateTimeButton: Button = findViewById(R.id.choose_date_time_button)
        chooseDateTimeButton.setOnClickListener {
            showDateTimePicker()
        }

        // Activity result launcher for capturing an image
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Image capture was successful, you can use capturedImageUri to handle the image.
            }
        }

        // Handle the Capture Photo button click
        capturePhotoButton.setOnClickListener {
            // Create a temporary file to store the captured image
            val imageFile = File(externalMediaDirs.first(), "observation.jpg")
            capturedImageUri = Uri.fromFile(imageFile)

            // Launch the camera to capture an image
            takePictureLauncher.launch(capturedImageUri)
        }

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
                val observation = BirdObservation(species, dateTime, location, notes, capturedImageUri)

                // Add the observation to the list
                birdObservations.add(observation)

                // Optionally, you can clear the input fields for the next entry
                speciesEditText.text.clear()
                dateTimeEditText.text.clear()
                locationEditText.text.clear()
                notesEditText.text.clear()
                capturedImageUri = null

                // Optionally, show a message to the user that the observation was saved
                // (e.g., using a Toast message).
            } else {
                // Handle validation errors (e.g., show an error message to the user).
            }
        }

    }
    fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedDateTime = "$year-$month-$dayOfMonth $hourOfDay:$minute"
                dateTimeEditText.setText(selectedDateTime)
            }, hour, minute, true)
            timePickerDialog.show()
        }, year, month, day)
        datePickerDialog.show()
    }
}
