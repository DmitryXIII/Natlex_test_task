package com.avacodo.natlextesttask.domain

private const val FORMAT_PATTERN = "%.2f"

class WeatherUnitsMapper {
    fun mapCelsiusToFahrenheit(temperatureInCelsius: Double): Double {
        return String
            .format(FORMAT_PATTERN, ((temperatureInCelsius * 9.0 / 5.0) + 32))
            .replace(",", ".")
            .toDouble()
    }
}