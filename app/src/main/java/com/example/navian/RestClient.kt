package com.example.navian

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EBirdService {
    @GET("data/obs/geo/recent")
    fun getNearbyHotspots(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("maxResults") maxResults: Int,
        @Query("dist") distance: Double,
        @Query("back") daysBack: Int,
        @Query("key") apiKey: String
    ): Call<List<Hotspot>>
}