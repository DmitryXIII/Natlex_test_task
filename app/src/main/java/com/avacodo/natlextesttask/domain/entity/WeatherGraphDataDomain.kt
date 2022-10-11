package com.avacodo.natlextesttask.domain.entity

data class WeatherGraphDataDomain(
    val locationID: String,
    val graphData: GraphDataDomain,
    val sliderData: SliderDataDomain,
)