package com.avacodo.natlextesttask.data.remote

import com.avacodo.natlextesttask.data.remote.dto.OpenWeatherMapDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_PATH = "data/2.5/weather"

interface OpenWeatherMapApi {
    @GET(BASE_PATH)
    fun getWeatherByLocationNameAsync(
        @Query("q") locationName: String,
    ): Deferred<OpenWeatherMapDto>

    @GET(BASE_PATH)
    fun getWeatherByLocationCoordsAsync(
        @Query("lat") locationLatitude: Double,
        @Query("lon") locationLongitude: Double,
    ): Deferred<OpenWeatherMapDto>
}