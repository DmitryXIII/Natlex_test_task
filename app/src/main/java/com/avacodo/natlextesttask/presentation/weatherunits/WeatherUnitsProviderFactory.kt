package com.avacodo.natlextesttask.presentation.weatherunits

class WeatherUnitsProviderFactory {
    fun initWeatherUnitsProvider(isCelsiusRequired: Boolean): WeatherUnitsProvider {
        return if (isCelsiusRequired) {
            CelsiusProvider()
        } else {
            FahrenheitProvider()
        }
    }
}