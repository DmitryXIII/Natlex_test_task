package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getRemoteWeatherData(locationName: String): WeatherModelDomain

    suspend fun getRemoteWeatherData(
        locationLatitude: Double,
        locationLongitude: Double,
    ): WeatherModelDomain

    suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain)

    suspend fun getLocalWeatherData(): Flow<List<WeatherModelDomain>>
}