package com.app.photoweather.weather.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "base") val base: String,
    @Json(name = "main") val main: Main,
    @Json(name = "visibility") val visibility: Long,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "dt") val dt: Long,
    @Json(name = "sys") val sys: Sys,
    @Json(name = "timezone") val timezone: Long,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "cod") val cod: Long
)