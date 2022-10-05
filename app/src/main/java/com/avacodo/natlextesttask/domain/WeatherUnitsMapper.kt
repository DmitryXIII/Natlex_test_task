package com.avacodo.natlextesttask.domain

import kotlin.math.roundToInt

private const val FORMAT_PATTERN = "%.2f"

class WeatherUnitsMapper {
    fun mapCelsiusToFahrenheit(temperatureInCelsius: Double): Double {
        return (((temperatureInCelsius * 9.0 / 5.0) + 32) * 10.0).roundToInt() / 10.0
    }

    fun roundValueToOneDecimal(temperatureInCelsius: Double): Double {
        return (temperatureInCelsius * 10.0).roundToInt() / 10.0
    }
}