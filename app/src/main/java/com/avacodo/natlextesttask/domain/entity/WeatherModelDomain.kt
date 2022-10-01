package com.avacodo.natlextesttask.domain.entity

data class WeatherModelDomain(
    val locationID: String,
    val locationName: String,
    val temperatureInCelsius: Double,
    val temperatureInFahrenheit: Double,
    val weatherMeasurementTime: Long,
)