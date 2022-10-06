package com.avacodo.natlextesttask.data.graph

import com.avacodo.natlextesttask.data.MapperToDomain
import com.avacodo.natlextesttask.data.local.WeatherDao
import com.avacodo.natlextesttask.domain.entity.GraphEntryDomain

class WeatherGraphRepositoryImpl(
    private val localDataSource: WeatherDao,
    private val mapper: MapperToDomain,
) : WeatherGraphRepository {
    override suspend fun getLocalWeatherDataByID(locationID: String): List<GraphEntryDomain> {
        return localDataSource.getLocalWeatherDataByID(locationID).map {
            mapper.mapWeatherLocalToGraphEntry(it)
        }
    }

    override suspend fun getMaxRequestTime(locationID: String): Long {
        return localDataSource.getMaxRequestTime(locationID)
    }

    override suspend fun getMinRequestTime(locationID: String): Long {
        return localDataSource.getMinRequestTime(locationID)
    }
}