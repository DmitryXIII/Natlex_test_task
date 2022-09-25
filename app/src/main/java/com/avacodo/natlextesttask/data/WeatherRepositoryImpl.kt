package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.remote.MapperToDomain
import com.avacodo.natlextesttask.data.remote.OpenWeatherMapApi
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class WeatherRepositoryImpl(
    private val remoteDataSource: OpenWeatherMapApi,
    private val mapper: MapperToDomain,
) : WeatherRepository {
    override suspend fun getRemoteWeatherData(locationName: String): WeatherModelDomain {
        return mapper.mapWeatherDtoToDomain(remoteDataSource.getWeatherByLocationNameAsync(
            locationName).await())
    }

    override suspend fun getRemoteWeatherData(
        locationLatitude: Double,
        locationLongitude: Double,
    ): WeatherModelDomain {
        return mapper.mapWeatherDtoToDomain(remoteDataSource.getWeatherByLocationCoordsAsync(
            locationLatitude, locationLongitude).await())
    }

    override suspend fun getLocalWeatherData(): WeatherModelDomain {
        TODO("Not yet implemented")
    }
}