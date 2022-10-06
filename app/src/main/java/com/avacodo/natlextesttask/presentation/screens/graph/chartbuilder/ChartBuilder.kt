package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat
import kotlin.random.Random

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm"
private const val CHART_TEMPERATURE_LINE_LABEL = "Температура"
private const val CHART_TEMPERATURE_LINE_WIDTH = 2f
private const val CHART_TEXT_SIZE = 12f
private const val X_AXIS_LABEL_ANGLE = 270f
private const val X_AXIS_GRANULARITY = 1f

class ChartBuilder {

    private val chartValueFormatter = ChartValueFormatter()

    private val xAxisValuesList = mutableListOf<String>()
    private val yAxisValuesList = mutableListOf<Entry>()
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)

    fun build(chartView: LineChart) {

        repeat(15) {
            val weatherData =
                WeatherModelDomain(
                    locationID = "Location_ID",
                    locationName = "Location_Name",
                    temperatureInCelsius = Random.nextDouble(5.0) + 10.0,
                    maxTempValueInCelsius = 15.0,
                    minTempValueInCelsius = 10.0,
                    weatherMeasurementTime = System.currentTimeMillis() + it * 15_200_000,
                    weatherRequestCount = 15
                )
            yAxisValuesList.add(Entry((it).toFloat(), weatherData.temperatureInCelsius.toFloat()))
            xAxisValuesList.add(dateFormat.format(weatherData.weatherMeasurementTime))
        }

        val set1 = LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
            valueFormatter = chartValueFormatter.getDataFormatter()
            valueTextSize = CHART_TEXT_SIZE
            lineWidth = CHART_TEMPERATURE_LINE_WIDTH
        }

        chartView.apply {
            setScaleEnabled(true)
            axisRight.isEnabled = false
            xAxis.valueFormatter = chartValueFormatter.getXAxisFormatter(xAxisValuesList)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.valueFormatter = chartValueFormatter.getYAxisFormatter(true)
            description = Description().apply {
                text = "Дата"
                textSize = CHART_TEXT_SIZE
            }
            xAxis.granularity = X_AXIS_GRANULARITY
            xAxis.labelRotationAngle = X_AXIS_LABEL_ANGLE
            axisLeft.setDrawGridLines(false)
            data = LineData(listOf(set1))
        }
    }
}