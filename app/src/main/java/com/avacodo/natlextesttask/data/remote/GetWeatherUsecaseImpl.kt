package com.avacodo.natlextesttask.data.remote

import com.avacodo.natlextesttask.data.WeatherRepository
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase

class GetWeatherUsecaseImpl(private val repository: WeatherRepository) : GetWeatherUsecase {
    override suspend fun getRemoteWeather(locationName: String): WeatherModelDomain {
        return repository.getRemoteWeatherData(locationName)
    }

    override suspend fun getRemoteWeather(
        latitude: Double,
        longitude: Double,
    ): WeatherModelDomain {
        return repository.getRemoteWeatherData(latitude, longitude)
    }
}