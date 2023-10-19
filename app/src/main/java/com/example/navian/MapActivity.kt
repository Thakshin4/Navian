package com.example.navian

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mapView: MapView? = null
    val hotspotsList = mutableListOf<Pair<Double, Double>>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Ebird
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ebird.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eBirdService = retrofit.create(EBirdService::class.java)

        val apiKey = "t1c9clcjj40b" // Replace with your eBird API key
        val latitude = 40.0 // Replace with user's latitude
        val longitude = -74.0 // Replace with user's longitude
        val maxResults = 10
        val distance = 25.0 // Search radius in kilometers
        val daysBack = 30 // Days back to search for sightings

        val call = eBirdService.getNearbyHotspots(
            latitude,
            longitude,
            maxResults,
            distance,
            daysBack,
            apiKey
        )

        call.enqueue(object : Callback<List<Hotspot>> {
            override fun onResponse(call: Call<List<Hotspot>>, response: Response<List<Hotspot>>) {
                if (response.isSuccessful) {
                    val hotspots = response.body()

                    // Process the list of hotspots and extract latitude and longitude
                    hotspots?.forEach { hotspot ->
                        val latitude = hotspot.locLatitude
                        val longitude = hotspot.locLongitude
                        hotspotsList.add(Pair(latitude, longitude))
                    }

                    // Now, hotspotsList contains pairs of latitude and longitude
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<List<Hotspot>>, t: Throwable) {
                // Handle network failure
            }
        })


        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Check for location permission
        if (checkLocationPermission()) {
            // If permission is granted, get the current location
            getCurrentLocation()
        } else {
            // Request location permission
            requestLocationPermission()
        }

        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) { addAnnotationToMap() }
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) { addAnnotationsForHotspots(hotspotsList) }
    }

    private fun addAnnotationToMap() {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MapActivity,
            R.drawable.baseline_navigation_24
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)

            // Check for location permission
            if (checkLocationPermission()) {
                // If permission is granted, get the current location
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val location: Location? = task.result
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Current Location")
                        alertDialog.setMessage(location.toString())
                        alertDialog.setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                        if (location != null) {
                            // Define a geographic coordinate based on the current location
                            val currentLocation = Point.fromLngLat(location.longitude, location.latitude)


                            // Set options for the resulting symbol layer.
                            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                                .withPoint(currentLocation)
                                .withIconImage(it)

                            // Add the resulting pointAnnotation to the map.
                            pointAnnotationManager?.create(pointAnnotationOptions)
                        } else {
                            Log.e("Location", "Location is null")
                        }
                    } else {
                        Log.e("Location", "Location task unsuccessful")
                    }
                }
            } else {
                // Request location permission if not granted
                requestLocationPermission()
            }
        }
    }
    private fun addAnnotationsForHotspots(hotspots: MutableList<Pair<Double, Double>>) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        for (hotspot in hotspots)
        {
            bitmapFromDrawableRes(
                this@MapActivity,
                R.drawable.baseline_visibility_24
            )?.let {
                val annotationApi = mapView?.annotations
                val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
                // Set options for the resulting symbol layer.
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    // Define a geographic coordinate.
                    .withPoint(Point.fromLngLat(hotspot.first, hotspot.second))
                    // Specify the bitmap you assigned to the point annotation
                    // The bitmap will be added to map style automatically.
                    .withIconImage(it)
                // Add the resulting pointAnnotation to the map.
                pointAnnotationManager?.create(pointAnnotationOptions)
            }
        }
    }


    // Define a custom hotspot icon (you can place this outside the MapActivity class)
    fun getHotspotIcon(): Int {
        // Define your custom hotspot icon here
        return R.drawable.baseline_visibility_24 // This should correspond to an icon in your map style
    }

    private fun checkLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(this, permission) == granted
    }

    private fun requestLocationPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val requestCode = 123 // You can use any request code

        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun getCurrentLocation() {
        try {
            val locationTask: Task<Location> = fusedLocationClient.lastLocation

            locationTask.addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val location: Location? = task.result
                    if (location != null) {
                        // Use the current location (location.latitude and location.longitude)
                        Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                    } else {
                        Log.e("Location", "Location is null")
                    }
                } else {
                    Log.e("Location", "Location task unsuccessful")
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}