package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity
import com.avacodo.natlextesttask.data.remote.dto.OpenWeatherMapDto
import com.avacodo.natlextesttask.domain.entity.GraphEntryDomain
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain

class MapperToDomain {
    fun mapWeatherDtoToDomain(weatherMapDto: OpenWeatherMapDto): WeatherModelDomain {
        return with(weatherMapDto) {
            WeatherModelDomain(
                locationID = locationID,
                locationName = locationName,
                temperatureInCelsius = main.temp,
                maxTempValueInCelsius = main.temp,
                minTempValueInCelsius = main.temp,
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
                maxTempValueInCelsius = maxTemp,
                minTempValueInCelsius = minTemp,
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
                weatherMeasurementTime = weatherMeasurementTime,
            )
        }
    }

    fun mapWeatherLocalToGraphEntry(weatherLocalEntity: WeatherLocalEntity): GraphEntryDomain {
        return with(weatherLocalEntity) {
            GraphEntryDomain(
                locationID = locationID,
                temperature = temperatureInCelsius,
                weatherRequestTimeL = weatherMeasurementTime,
            )
        }
    }
}