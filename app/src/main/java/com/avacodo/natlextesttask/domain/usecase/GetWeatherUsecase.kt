package com.avacodo.natlextesttask.domain.usecase

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

interface GetWeatherUsecase {
    suspend fun getRemoteWeather(locationName: String) : WeatherModelDomain
    suspend fun getRemoteWeather(latitude: Double, longitude: Double): WeatherModelDomain
}