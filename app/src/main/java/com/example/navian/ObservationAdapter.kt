package com.example.navian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ObservationAdapter(private val observations: List<BirdObservation>) :
    RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder>() {

    inner class ObservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val speciesTextView: TextView = itemView.findViewById(R.id.speciesTextView)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.dateTimeTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        val notesTextView: TextView = itemView.findViewById(R.id.notesTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.observation_item, parent, false)
        return ObservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        val observation = observations[position]
        holder.speciesTextView.text = observation.species
        holder.dateTimeTextView.text = observation.dateTime
        holder.locationTextView.text = observation.location
        holder.notesTextView.text = observation.notes

        // Load and display the image if available
        if (observation.imageUri != null) {
            holder.imageView.setImageURI(observation.imageUri)
        } else {
            // Hide the ImageView if there's no image
            holder.imageView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return observations.size
    }
}
