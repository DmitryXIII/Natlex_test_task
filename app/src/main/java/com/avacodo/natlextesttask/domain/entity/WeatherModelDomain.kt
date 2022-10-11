package com.avacodo.natlextesttask.domain.entity

data class WeatherModelDomain(
    val locationID: String,
    val locationName: String,
    val temperatureInCelsius: Double,
    val maxTempValueInCelsius: Double,
    val minTempValueInCelsius: Double,
    val weatherMeasurementTime: Long,
    val weatherRequestCount: Int,
)