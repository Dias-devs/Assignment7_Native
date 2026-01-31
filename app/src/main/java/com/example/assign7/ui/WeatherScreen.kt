package com.example.assign7.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assign7.util.NetworkResult
import com.example.assign7.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        // Example: Almaty coordinates
        viewModel.loadWeather(43.238949, 76.889709)
    }

    when (state) {
        is NetworkResult.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is NetworkResult.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading weather")
            }
        }

        is NetworkResult.Success -> {
            val data = (state as NetworkResult.Success).data
            val offline = (state as NetworkResult.Success).offline

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                if (offline) {
                    Text(
                        text = "Offline mode",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Text("Temperature: ${data.currentWeather.temperature}°C")
                Text("Wind: ${data.currentWeather.windspeed} km/h")

                Spacer(modifier = Modifier.height(16.dp))

                Text("3-Day Forecast", style = MaterialTheme.typography.titleMedium)

                for (i in 0..2) {
                    Text(
                        "${data.daily.time[i]}  " +
                                "Min: ${data.daily.minTemp[i]}°C  " +
                                "Max: ${data.daily.maxTemp[i]}°C"
                    )
                }
            }
        }
    }
}