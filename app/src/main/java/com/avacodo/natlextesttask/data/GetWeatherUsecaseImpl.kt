package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.remote.WeatherSearchRepository
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase
import kotlinx.coroutines.flow.Flow

class GetWeatherUsecaseImpl(private val repository: WeatherSearchRepository) : GetWeatherUsecase {
    override suspend fun getRemoteWeather(locationName: String): WeatherModelDomain {
        return repository.getRemoteWeatherData(locationName.trim())
    }

    override suspend fun getRemoteWeather(
        latitude: Double,
        longitude: Double,
    ): WeatherModelDomain {
        return repository.getRemoteWeatherData(latitude, longitude)
    }

    override suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain) {
        repository.addLocalWeatherData(weatherModelDomain)
    }

    override suspend fun getLocalWeather(): Flow<List<WeatherModelDomain>> {
        return repository.getLocalWeatherData()
    }
}