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

data class Coord(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
)

data class Weather(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

data class Main(
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feels_like: Float,
    @Json(name = "temp_min") val temp_min: Float,
    @Json(name = "temp_max") val temp_max: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)

data class Wind(
    @Json(name = "speed") val speed: Float,
    @Json(name = "deg") val deg: Int
)

data class Clouds(
    @Json(name = "all") val all: Int
)

data class Sys(
    @Json(name = "type") val type: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: Int,
    @Json(name = "sunset") val sunset: Int
)
