package com.avacodo.natlextesttask.domain.entity

data class WeatherModelDomain(
    val locationID: String,
    val locationName: String,
    val locationLatitude: Double,
    val locationLongitude: Double,
    val temperatureInCelsius: Double,
    val temperatureInFahrenheit: Double,
    val weatherTime: Long
)