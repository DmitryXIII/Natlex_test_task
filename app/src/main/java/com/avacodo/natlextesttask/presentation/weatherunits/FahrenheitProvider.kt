package com.avacodo.natlextesttask.presentation.weatherunits

import android.content.Context
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class FahrenheitProvider : WeatherUnitsProvider {
    override fun provideWeatherValue(
        context: Context,
        weatherData: WeatherModelDomain,
    ): String {
        return context.getString(
            R.string.weather_value_in_fahrenheit,
            weatherData.temperatureInFahrenheit
        )
    }
}