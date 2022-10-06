package com.avacodo.natlextesttask.domain.weatherunits

interface WeatherUnitsProvider {
    fun provideWeatherValue(temperature: Double): String
    fun provideMaxTempValue(temperature: Double): String
    fun provideMinTempValue(temperature: Double): String
}