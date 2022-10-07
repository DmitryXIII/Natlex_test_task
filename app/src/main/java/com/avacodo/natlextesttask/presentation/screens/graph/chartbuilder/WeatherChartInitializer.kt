package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm"
private const val CHART_TEMPERATURE_LINE_LABEL = "Температура"
private const val CHART_TEMPERATURE_LINE_WIDTH = 2f
private const val X_AXIS_LABEL_ANGLE = 270f
private const val X_AXIS_GRANULARITY = 1f
private const val CHART_TEXT_SIZE = 12f

class WeatherChartInitializer(
    private val chartValueFormatter: ChartValueFormatter,
) : ChartInitializer<WeatherGraphDataDomain> {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
    private var xAxisValuesList = listOf<String>()
    private var yAxisValuesList = listOf<Entry>()
    override fun setRequiredWeatherUnits(isCelsiusRequired: Boolean) {
        chartValueFormatter.initWeatherUnitsProvider(isCelsiusRequired)
    }

    override fun initXAxis(xAxis: XAxis) {
        xAxis.apply {
            setDrawGridLines(false)
            valueFormatter = chartValueFormatter.getXAxisFormatter(xAxisValuesList)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = X_AXIS_GRANULARITY
            labelRotationAngle = X_AXIS_LABEL_ANGLE
        }
    }

    override fun initAxisLeft(yAxis: YAxis) {
        yAxis.apply {
            setDrawGridLines(false)
            valueFormatter = chartValueFormatter.getYAxisFormatter()
        }
    }

    override fun initAxisRight(yAxis: YAxis) {
        yAxis.isEnabled = false
    }

    override fun setChartData(chartData: WeatherGraphDataDomain) {
        xAxisValuesList = chartData.weatherData.map { graphEntryDomain ->
            dateFormat.format(graphEntryDomain.weatherRequestTime)
        }

        yAxisValuesList = chartData.weatherData.mapIndexed { index, graphEntryDomain ->
            Entry(index.toFloat(), graphEntryDomain.temperature.toFloat())
        }
    }

    override fun initDataSet(): LineDataSet {
        return LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
            valueFormatter = chartValueFormatter.getDataFormatter()
            valueTextSize = CHART_TEXT_SIZE
            lineWidth = CHART_TEMPERATURE_LINE_WIDTH
        }
    }
}