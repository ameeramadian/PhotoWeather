package com.app.photoweather.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.photoweather.weather.model.WeatherResponse
import com.app.photoweather.weather.repository.WeatherRepository
import com.app.photoweather.weather.ui.iWeatherView

class WeatherResponseViewModel : ViewModel() {
    var weatherRepository = WeatherRepository()

    val weatherResponse: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>()
    }

    fun fetchWeatherData(
        view: iWeatherView,
        lat: Double,
        lon: Double
    ) {
        view.showLoading()
        weatherRepository.getCurrentWeatherData(lat, lon, {
            weatherResponse.postValue(it)
            view.hideLoading()
        }, {
            view.hideLoading()
        })
    }
}