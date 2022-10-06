package com.avacodo.natlextesttask.data.graph

import com.avacodo.natlextesttask.domain.entity.GraphEntryDomain

interface WeatherGraphRepository {
    suspend fun getLocalWeatherDataByID(locationID: String): List<GraphEntryDomain>
    suspend fun getMaxRequestTime(locationID: String): Long
    suspend fun getMinRequestTime(locationID: String): Long
}