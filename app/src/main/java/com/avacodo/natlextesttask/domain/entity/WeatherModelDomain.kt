package com.avacodo.natlextesttask.domain.entity

data class WeatherModelDomain(
    val locationID: String,
    val locationName: String,
    val temperatureInCelsius: Double,
    val temperatureInFahrenheit: Double,
    val maxTempValue: Double,
    val minTempValue: Double,
    val weatherMeasurementTime: Long,
    val weatherRequestCount: Int,
)