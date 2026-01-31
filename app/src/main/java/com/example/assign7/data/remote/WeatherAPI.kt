package com.example.assign7.data.remote

import com.example.assign7.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longtitude") lon: Double,
        @Query("current_weather") current: Boolean = true,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}