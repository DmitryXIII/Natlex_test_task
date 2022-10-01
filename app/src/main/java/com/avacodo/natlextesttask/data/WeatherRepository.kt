package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

interface WeatherRepository {
    suspend fun getRemoteWeatherData(locationName: String): WeatherModelDomain

    suspend fun getRemoteWeatherData(
        locationLatitude: Double,
        locationLongitude: Double,
    ): WeatherModelDomain

    suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain)

    suspend fun getLocalWeatherData(): List<WeatherModelDomain>
}