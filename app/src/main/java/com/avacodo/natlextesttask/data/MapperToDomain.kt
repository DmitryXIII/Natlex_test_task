package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity
import com.avacodo.natlextesttask.data.remote.dto.OpenWeatherMapDto
import com.avacodo.natlextesttask.domain.WeatherUnitsMapper
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class MapperToDomain {
    fun mapWeatherDtoToDomain(weatherMapDto: OpenWeatherMapDto): WeatherModelDomain {
        return with(weatherMapDto) {
            WeatherModelDomain(
                locationID = locationID,
                locationName = locationName,
                temperatureInCelsius = main.temp,
                temperatureInFahrenheit = WeatherUnitsMapper().mapCelsiusToFahrenheit(main.temp),
                weatherMeasurementTime = System.currentTimeMillis() / 1000,
            )
        }
    }

    fun mapWeatherLocalToDomain(weatherLocalEntity: WeatherLocalEntity): WeatherModelDomain {
        return with(weatherLocalEntity) {
            WeatherModelDomain(
                locationID = locationID,
                locationName = locationName,
                temperatureInCelsius = temperatureInCelsius,
                temperatureInFahrenheit = temperatureInFahrenheit,
                weatherMeasurementTime = weatherMeasurementTime,
            )
        }
    }

    fun mapWeatherDomainToLocal(weatherModelDomain: WeatherModelDomain): WeatherLocalEntity {
        return with(weatherModelDomain) {
            WeatherLocalEntity(
                locationID = locationID,
                locationName = locationName,
                temperatureInCelsius = temperatureInCelsius,
                temperatureInFahrenheit = temperatureInFahrenheit,
                weatherMeasurementTime = weatherMeasurementTime,
            )
        }
    }
}