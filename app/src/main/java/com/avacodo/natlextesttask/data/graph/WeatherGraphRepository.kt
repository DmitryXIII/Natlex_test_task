package com.avacodo.natlextesttask.data.graph

import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity

interface WeatherGraphRepository {
    suspend fun getLocalWeatherDataByID(locationID: String): List<WeatherLocalEntity>
    suspend fun getLocalWeatherDataByRange(
        locationID: String,
        timeFrom: Long,
        timeTo: Long,
    ): List<WeatherLocalEntity>

    suspend fun getMaxRequestTime(locationID: String): Long
    suspend fun getMinRequestTime(locationID: String): Long
}