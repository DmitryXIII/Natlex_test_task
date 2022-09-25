package com.avacodo.natlextesttask.data.remote

import com.avacodo.natlextesttask.data.remote.dto.OpenWeatherMapDto
import com.avacodo.natlextesttask.domain.WeatherUnitsMapper
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class MapperToDomain {
    fun mapWeatherDtoToDomain(weatherMapDto: OpenWeatherMapDto): WeatherModelDomain {
        return with(weatherMapDto) {
            WeatherModelDomain(
                locationID = id,
                locationName = name,
                locationLatitude = coord.lat,
                locationLongitude = coord.lon,
                temperatureInCelsius = main.temp,
                temperatureInFahrenheit = WeatherUnitsMapper().mapCelsiusToFahrenheit(main.temp),
                weatherTime = dt
            )
        }
    }
}