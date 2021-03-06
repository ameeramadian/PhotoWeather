package com.app.photoweather.weather.model

import com.squareup.moshi.Json

data class Wind(
    @Json(name = "speed") val speed: Float,
    @Json(name = "deg") val deg: Int
)