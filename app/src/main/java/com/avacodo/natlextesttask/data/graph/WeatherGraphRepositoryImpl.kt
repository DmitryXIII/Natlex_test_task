package com.avacodo.natlextesttask.data.graph

import com.avacodo.natlextesttask.data.local.WeatherDao
import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity

class WeatherGraphRepositoryImpl(
    private val localDataSource: WeatherDao
) : WeatherGraphRepository {
    override suspend fun getLocalWeatherDataByID(locationID: String): List<WeatherLocalEntity> {
        return localDataSource.getLocalWeatherDataByID(locationID)
    }

    override suspend fun getLocalWeatherDataByRange(
        locationID: String,
        timeFrom: Long,
        timeTo: Long,
    ): List<WeatherLocalEntity> {
        return localDataSource.getLocalWeatherDataByTimeRange(locationID, timeFrom, timeTo)
    }

    override suspend fun getMaxRequestTime(locationID: String): Long {
        return localDataSource.getMaxRequestTime(locationID)
    }

    override suspend fun getMinRequestTime(locationID: String): Long {
        return localDataSource.getMinRequestTime(locationID)
    }
}