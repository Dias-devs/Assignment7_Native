package com.example.assign7.data.repo

import com.example.assign7.data.model.WeatherResponse
import com.example.assign7.data.remote.WeatherAPI

class WeatherRepo(private val api: WeatherAPI) {
    suspend fun fetchWeather(lat: Double, lon:  Double): WeatherResponse {
        return api.getWeather(lat, lon)
    }
}