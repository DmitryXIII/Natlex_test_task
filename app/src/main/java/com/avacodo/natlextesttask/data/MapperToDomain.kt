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
                maxTempValue = main.temp,
                minTempValue = main.temp,
                weatherMeasurementTime = System.currentTimeMillis(),
                weatherRequestCount = 1,
            )
        }
    }

    fun mapWeatherLocalToDomain(
        weatherLocalEntity: WeatherLocalEntity,
        maxTemp: Double,
        minTemp: Double,
        requestCount: Int,
    ): WeatherModelDomain {
        return with(weatherLocalEntity) {
            WeatherModelDomain(
                locationID = locationID,
                locationName = locationName,
                temperatureInCelsius = temperatureInCelsius,
                temperatureInFahrenheit = temperatureInFahrenheit,
                maxTempValue = maxTemp,
                minTempValue = minTemp,
                weatherMeasurementTime = weatherMeasurementTime,
                weatherRequestCount = requestCount,
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