package com.example.navian

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class UnitConverter {
    companion object {
        fun kilometersToMiles(kilometers: Double): Double {
            return kilometers * 0.621371
        }
    }
}
