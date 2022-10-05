package com.avacodo.natlextesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenWeatherMapDto(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val weatherMeasurementTime: Long,
    @SerializedName("id")
    val locationID: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val locationName: String,
)