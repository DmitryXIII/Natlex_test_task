package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.graph.WeatherGraphRepository
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase

class WeatherGraphUsecaseImpl(private val repository: WeatherGraphRepository) :
    WeatherGraphUsecase {
    override suspend fun getWeatherGraphData(locationID: String): WeatherGraphDataDomain {
        return WeatherGraphDataDomain(
            weatherData = repository.getLocalWeatherDataByID(locationID),
            maxRequestTime = repository.getMaxRequestTime(locationID),
            minRequestTime = repository.getMinRequestTime(locationID)
        )
    }
}