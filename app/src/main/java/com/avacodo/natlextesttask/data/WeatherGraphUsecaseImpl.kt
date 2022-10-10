package com.avacodo.natlextesttask.data

import com.avacodo.natlextesttask.data.graph.WeatherGraphRepository
import com.avacodo.natlextesttask.domain.entity.GraphDataDomain
import com.avacodo.natlextesttask.domain.entity.SliderDataDomain
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase
import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat


private const val MILLISECONDS_IN_MINUTE = 60000
private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy  -  HH:mm"

class WeatherGraphUsecaseImpl(private val repository: WeatherGraphRepository, private val mapper: MapperToDomain) :
    WeatherGraphUsecase {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)

    override suspend fun getWeatherGraphData(locationID: String): WeatherGraphDataDomain {
        val weatherLocalEntityList = repository.getLocalWeatherDataByID(locationID)
        val startTime = repository.getMinRequestTime(locationID) / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val endTime = repository.getMaxRequestTime(locationID) / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE



        val xAxisValuesList = weatherLocalEntityList.map { weatherLocalEntity ->
            dateFormat.format(weatherLocalEntity.weatherMeasurementTime)
        }

        val yAxisValuesList = weatherLocalEntityList.mapIndexed { index, weatherLocalEntity ->
            Entry(index.toFloat(), weatherLocalEntity.temperatureInCelsius.toFloat())
        }

        val requestTimesList = mutableListOf<Long>()

        val minutesCount = ((endTime - startTime) / MILLISECONDS_IN_MINUTE).toInt() + 1

        for (i in 0..minutesCount) {
            requestTimesList.add(startTime + i * MILLISECONDS_IN_MINUTE)
        }

        val graphData = GraphDataDomain(
            xAxisValuesList = xAxisValuesList,
            yAxisValuesList = yAxisValuesList
        )

        val sliderData = SliderDataDomain(
            maxRequestTime = endTime,
            minRequestTime = startTime,
            minutesRange = minutesCount,
            requestTimeList = requestTimesList
        )

        return WeatherGraphDataDomain(
            locationID = locationID,
            graphData = graphData,
            sliderData = sliderData
        )
    }

    override suspend fun getWeatherGraphDataByRange(
        locationID: String,
        timeFrom: Long,
        timeTo: Long,
    ): WeatherGraphDataDomain {



        val dataBaseList = repository.getLocalWeatherDataByRange(locationID, timeFrom, timeTo)

        val xAxisValuesList = dataBaseList.map { weatherLocalEntity ->
            dateFormat.format(weatherLocalEntity.weatherMeasurementTime)
        }

        val yAxisValuesList = dataBaseList.mapIndexed { index, weatherLocalEntity ->
            Entry(index.toFloat(), weatherLocalEntity.temperatureInCelsius.toFloat())
        }

        val requestTimesList = mutableListOf<Long>()
        val startTime =
            repository.getMinRequestTime(locationID) / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val endTime =
            repository.getMaxRequestTime(locationID) / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val minutesCount = ((endTime - startTime) / MILLISECONDS_IN_MINUTE).toInt() + 1

        for (i in 0..minutesCount) {
            requestTimesList.add(startTime + i * MILLISECONDS_IN_MINUTE)
        }

        val graphData = GraphDataDomain(
            xAxisValuesList = xAxisValuesList,
            yAxisValuesList = yAxisValuesList
        )

        val sliderData = SliderDataDomain(
            maxRequestTime = endTime,
            minRequestTime = startTime,
            minutesRange = minutesCount,
            requestTimeList = requestTimesList
        )

        return WeatherGraphDataDomain(
            locationID = locationID,
            graphData = graphData,
            sliderData = sliderData,
        )
    }
}