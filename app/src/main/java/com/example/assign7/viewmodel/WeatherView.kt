package com.example.assign7.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assign7.data.model.WeatherResponse
import com.example.assign7.data.remote.Retrofit
import com.example.assign7.data.repo.WeatherRepo
import com.example.assign7.util.CacheManager
import com.example.assign7.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherRepo(Retrofit.api)
    private val cache = CacheManager(application)

    private val _state = MutableStateFlow<NetworkResult<WeatherResponse>>(NetworkResult.Loading)
    val state: StateFlow<NetworkResult<WeatherResponse>> = _state

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _state.value = NetworkResult.Loading
            try {
                val response = repository.fetchWeather(lat, lon)
                cache.saveWeather(Json.encodeToString(response))
                _state.value = NetworkResult.Success(response)
            } catch (e: Exception) {
                val cached = cache.getCachedWeather()
                if (cached != null) {
                    val data = Json.decodeFromString<WeatherResponse>(cached)
                    _state.value = NetworkResult.Success(data, offline = true)
                } else {
                    _state.value = NetworkResult.Error("Failed to load weather")
                }
            }
        }
    }
}