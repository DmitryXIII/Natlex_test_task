package com.avacodo.natlextesttask.presentation.weatherunits

import android.content.Context
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class CelsiusProvider : BaseUnitsProvider() {
    override fun provideWeatherValue(
        context: Context,
        weatherData: WeatherModelDomain,
    ): String {
        return context.getString(
            R.string.weather_value_in_celsius,
            unitsMapper.roundValueToOneDecimal(weatherData.temperatureInCelsius)
        )
    }

    override fun provideMaxTempValue(context: Context, weatherData: WeatherModelDomain): String {
        return context.getString(
            R.string.max_temp_value_in_celsius,
            unitsMapper.roundValueToOneDecimal(weatherData.maxTempValueInCelsius)
        )
    }

    override fun provideMinTempValue(context: Context, weatherData: WeatherModelDomain): String {
        return context.getString(
            R.string.min_temp_value_in_celsius,
            unitsMapper.roundValueToOneDecimal(weatherData.minTempValueInCelsius)
        )
    }
}