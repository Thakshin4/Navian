package com.example.navian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewObservationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_observations)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val observations = getObservations() // Replace with your data source

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ObservationAdapter(observations)
    }

    private fun getObservations(): List<BirdObservation> {
        // Replace this with your logic to fetch or retrieve bird observations
        val observations = birdObservations

        // Add sample observations (replace with your actual data retrieval logic)
        observations.add(
            BirdObservation("Sparrow", "2023-10-01 10:00 AM", "Park", "Saw a sparrow on a tree.")
        )
        observations.add(
            BirdObservation("Blue Jay", "2023-10-02 2:30 PM", "Garden", "Blue jay spotted near the feeder.")
        )

        // You can fetch data from a database, shared preferences, or any other source here

        return observations
    }

}
