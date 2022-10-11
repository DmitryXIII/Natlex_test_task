package com.avacodo.natlextesttask.domain.usecase

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import kotlinx.coroutines.flow.Flow

interface GetWeatherUsecase {
    suspend fun getRemoteWeather(locationName: String) : WeatherModelDomain
    suspend fun getRemoteWeather(latitude: Double, longitude: Double): WeatherModelDomain
    suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain)
    suspend fun getLocalWeather(): Flow<List<WeatherModelDomain>>
}