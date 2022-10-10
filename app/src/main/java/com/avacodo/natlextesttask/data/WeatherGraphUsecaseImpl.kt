package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.graph.WeatherGraphRepository
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase

class WeatherGraphUsecaseImpl(
    private val repository: WeatherGraphRepository,
    private val mapper: MapperToDomain,
) : WeatherGraphUsecase {

    override suspend fun getWeatherGraphData(locationID: String): WeatherGraphDataDomain {
        return WeatherGraphDataDomain(
            locationID = locationID,
            graphData = mapper.mapToGraphData(repository.getLocalWeatherDataByID(locationID)),
            sliderData = mapper.mapToSliderData(
                repository.getMinRequestTime(locationID),
                repository.getMaxRequestTime(locationID))
        )
    }

    override suspend fun getWeatherGraphDataByRange(
        locationID: String,
        timeFrom: Long,
        timeTo: Long,
    ): WeatherGraphDataDomain {
        return WeatherGraphDataDomain(
            locationID = locationID,
            graphData = mapper.mapToGraphData(repository.getLocalWeatherDataByRange(
                locationID,
                timeFrom,
                timeTo)),
            sliderData = mapper.mapToSliderData(
                repository.getMinRequestTime(locationID),
                repository.getMaxRequestTime(locationID))
        )
    }
}