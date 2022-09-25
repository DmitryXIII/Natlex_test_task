package com.avacodo.natlextesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenWeatherMapDto(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("id")
    val id: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
)