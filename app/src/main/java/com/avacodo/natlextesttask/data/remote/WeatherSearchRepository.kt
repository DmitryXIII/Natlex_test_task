package com.avacodo.natlextesttask.data.remote

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import kotlinx.coroutines.flow.Flow

interface WeatherSearchRepository {
    suspend fun getRemoteWeatherData(locationName: String): WeatherModelDomain

    suspend fun getRemoteWeatherData(
        locationLatitude: Double,
        locationLongitude: Double,
    ): WeatherModelDomain

    suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain)

    suspend fun getLocalWeatherData(): Flow<List<WeatherModelDomain>>
}