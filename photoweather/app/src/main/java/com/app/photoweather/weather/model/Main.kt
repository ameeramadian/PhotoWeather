package com.app.photoweather.weather.model

import com.squareup.moshi.Json

data class Main(
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feels_like: Float,
    @Json(name = "temp_min") val temp_min: Float,
    @Json(name = "temp_max") val temp_max: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)