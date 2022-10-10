package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity
import com.avacodo.natlextesttask.data.remote.dto.OpenWeatherMapDto
import com.avacodo.natlextesttask.domain.entity.GraphDataDomain
import com.avacodo.natlextesttask.domain.entity.SliderDataDomain
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy  -  HH:mm"
private const val MILLISECONDS_IN_MINUTE = 60000

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

    fun mapToGraphData(weatherLocalEntityList: List<WeatherLocalEntity>): GraphDataDomain {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)

        val xAxisValuesList = weatherLocalEntityList.map { weatherLocalEntity ->
            dateFormat.format(weatherLocalEntity.weatherMeasurementTime)
        }

        val yAxisValuesList = weatherLocalEntityList.mapIndexed { index, weatherLocalEntity ->
            Entry(index.toFloat(), weatherLocalEntity.temperatureInCelsius.toFloat())
        }

        return GraphDataDomain(
            xAxisValuesList = xAxisValuesList,
            yAxisValuesList = yAxisValuesList
        )
    }

    fun mapToSliderData(
        startTimeInMillis: Long,
        endTimeInMillis: Long,
    ): SliderDataDomain {
        val requestTimesList = mutableListOf<Long>()
        val startTimeInMinutes = startTimeInMillis / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val endTimeInMinutes = endTimeInMillis / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE

        val minutesCount =
            ((endTimeInMinutes - startTimeInMinutes) / MILLISECONDS_IN_MINUTE).toInt() + 1

        for (i in 0..minutesCount) {
            requestTimesList.add(startTimeInMinutes + i * MILLISECONDS_IN_MINUTE)
        }
        return SliderDataDomain(
            maxRequestTime = endTimeInMinutes,
            minRequestTime = startTimeInMinutes,
            minutesRange = minutesCount,
            requestTimeList = requestTimesList
        )
    }
}