package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.local.WeatherDao
import com.avacodo.natlextesttask.data.remote.OpenWeatherMapApi
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val remoteDataSource: OpenWeatherMapApi,
    private val localDataSource: WeatherDao,
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

    override suspend fun addLocalWeatherData(weatherModelDomain: WeatherModelDomain) {
        localDataSource.addWeatherData(mapper.mapWeatherDomainToLocal(weatherModelDomain))
    }

    override suspend fun getLocalWeatherData(): Flow<List<WeatherModelDomain>> {
        return localDataSource.getWeatherData().map { weatherLocalEntityList ->
            weatherLocalEntityList.map { weatherLocalEntity ->
                mapper.mapWeatherLocalToDomain(weatherLocalEntity)
            }
        }
    }
}