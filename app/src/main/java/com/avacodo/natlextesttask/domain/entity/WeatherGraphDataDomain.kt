package com.avacodo.natlextesttask.domain.entity

data class WeatherGraphDataDomain(
    val weatherData: List<GraphEntryDomain>,
    val maxRequestTime: Long,
    val minRequestTime: Long,
)