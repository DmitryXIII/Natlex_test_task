package com.avacodo.natlextesttask.domain.usecase

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

interface GetWeatherUsecase {
    suspend fun getWeatherByLocationName(locationName: String) : WeatherModelDomain
    suspend fun getWeatherByLocationCoords(latitude: Double, longitude: Double): WeatherModelDomain
}