package com.avacodo.natlextesttask.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Double
)