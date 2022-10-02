package com.avacodo.natlextesttask.presentation.weatherunits

import android.content.Context
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.WeatherUnitsMapper
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class FahrenheitProvider : WeatherUnitsProvider {
    private val mapperToFahrenheit = WeatherUnitsMapper()

    override fun provideWeatherValue(
        context: Context,
        weatherData: WeatherModelDomain,
    ): String {
        return context.getString(
            R.string.weather_value_in_fahrenheit,
            mapperToFahrenheit.mapCelsiusToFahrenheit(weatherData.temperatureInCelsius)
        )
    }

    override fun provideMaxTempValue(context: Context, weatherData: WeatherModelDomain): String {
        return context.getString(
            R.string.max_temp_value_in_fahrenheit,
            mapperToFahrenheit.mapCelsiusToFahrenheit(weatherData.maxTempValue)
        )
    }

    override fun provideMinTempValue(context: Context, weatherData: WeatherModelDomain): String {
        return context.getString(
            R.string.min_temp_value_in_fahrenheit,
            mapperToFahrenheit.mapCelsiusToFahrenheit(weatherData.minTempValue)
        )
    }
}