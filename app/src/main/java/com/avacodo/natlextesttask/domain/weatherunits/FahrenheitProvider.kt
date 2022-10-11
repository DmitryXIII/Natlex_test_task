package com.avacodo.natlextesttask.domain.weatherunits

class FahrenheitProvider : BaseUnitsProvider() {

    override fun provideWeatherValue(temperature: Double): String {
        return "${unitsMapper.mapCelsiusToFahrenheit(temperature)}° F"
    }

    override fun provideMaxTempValue(temperature: Double): String {
        return "max: ${unitsMapper.mapCelsiusToFahrenheit(temperature)}° F"
    }

    override fun provideMinTempValue(temperature: Double): String {
        return "min: ${unitsMapper.mapCelsiusToFahrenheit(temperature)}° F"
    }
}