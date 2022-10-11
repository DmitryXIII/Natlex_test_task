package com.avacodo.natlextesttask.domain.weatherunits

class WeatherUnitsProviderFactory {
    fun initWeatherUnitsProvider(isCelsiusRequired: Boolean): WeatherUnitsProvider {
        return if (isCelsiusRequired) {
            CelsiusProvider()
        } else {
            FahrenheitProvider()
        }
    }
}