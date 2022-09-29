package com.avacodo.natlextesttask.presentation.weatherunits

import android.content.Context
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

interface WeatherUnitsProvider {
    fun provideWeatherValue(context: Context, weatherData: WeatherModelDomain): String
}