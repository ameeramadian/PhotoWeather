package com.app.photoweather.weather.repository

import com.app.photoweather.BuildConfig
import com.app.photoweather.network_layer.RetrofitBuilder
import com.app.photoweather.weather.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    private val retrofit = RetrofitBuilder.build()
    private val client = retrofit.create(WeatherAPI::class.java)

    fun getCurrentWeatherData(
        lat: Double,
        lon: Double,
        successCallback: (WeatherResponse?) -> Unit,
        failureCallback: () -> Unit
    ) {
        client.getCurrentWeatherData(lat, lon, appID).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        successCallback(response.body())
                    }
                    else -> {
                        failureCallback()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                failureCallback()
            }
        })
    }

    companion object {
        private const val appID: String = BuildConfig.WEATHER_APP_ID
    }
}