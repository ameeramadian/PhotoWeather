package com.app.photoweather.weather.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.photoweather.weather.repository.WeatherRepository

class WeatherResponseViewModel : ViewModel() {
    var weatherRepository = WeatherRepository()

    val weatherResponse: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>()
    }

    fun fetchWeatherData(
        lat: Double,
        lon: Double
    ) {
        weatherRepository.getCurrentWeatherData(lat, lon, {
            weatherResponse.postValue(it)
        }, { })
    }
}