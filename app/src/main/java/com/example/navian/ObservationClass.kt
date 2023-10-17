package com.example.navian

import android.net.Uri

data class BirdObservation(
    val species: String,
    val dateTime: String,
    val location: String,
    val notes: String,
    val imageUri: Uri? = null
)
