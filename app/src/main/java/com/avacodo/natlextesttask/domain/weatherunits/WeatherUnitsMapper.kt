package com.avacodo.natlextesttask.domain.weatherunits

import kotlin.math.roundToInt

class WeatherUnitsMapper {
    fun mapCelsiusToFahrenheit(temperatureInCelsius: Double): Double {
        return roundValueToOneDecimal(((temperatureInCelsius * 9.0 / 5.0) + 32))
    }

    fun roundValueToOneDecimal(temperature: Double): Double {
        return (temperature * 10.0).roundToInt() / 10.0
    }
}