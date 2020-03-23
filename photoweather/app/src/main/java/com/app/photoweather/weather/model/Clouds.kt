package com.app.photoweather.weather.model

import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all") val all: Int
)