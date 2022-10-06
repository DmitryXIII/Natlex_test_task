package com.avacodo.natlextesttask.domain.usecase

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain

interface WeatherGraphUsecase {
    suspend fun getWeatherGraphData(locationID: String): WeatherGraphDataDomain
}