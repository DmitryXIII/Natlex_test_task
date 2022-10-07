package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProviderFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat

private const val CHART_DESCRIPTION = "Дата"
private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm"
private const val CHART_TEMPERATURE_LINE_LABEL = "Температура"
private const val CHART_TEMPERATURE_LINE_WIDTH = 2f
private const val X_AXIS_LABEL_ANGLE = 270f
private const val X_AXIS_GRANULARITY = 1f
private const val CHART_TEXT_SIZE = 12f

class WeatherChartBuilder(
) : ChartBuilder<WeatherGraphDataDomain> {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
    private lateinit var unitsProvider: WeatherUnitsProvider

    override fun build(
        chartView: LineChart,
        isCelsiusRequired: Boolean,
        weatherGraphDataDomain: WeatherGraphDataDomain,
    ) {
        unitsProvider = WeatherUnitsProviderFactory().initWeatherUnitsProvider(isCelsiusRequired)
        val xAxisValuesList = mutableListOf<String>()
        val yAxisValuesList = mutableListOf<Entry>()
        for (index in weatherGraphDataDomain.weatherData.indices) {
            yAxisValuesList.add(
                Entry(index.toFloat(),
                    weatherGraphDataDomain.weatherData[index].temperature.toFloat()))

            xAxisValuesList.add(
                dateFormat.format(weatherGraphDataDomain.weatherData[index].weatherRequestTime))
        }
        chartView.apply {

            axisLeft.apply {
                setDrawGridLines(false)
                valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return unitsProvider.provideWeatherValue(value.toDouble())
                    }
                }
            }

            axisRight.isEnabled = false

            xAxis.apply {
                setDrawGridLines(false)
                valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return xAxisValuesList[value.toInt()]
                    }
                }
                position = XAxis.XAxisPosition.BOTTOM
                granularity = X_AXIS_GRANULARITY
                labelRotationAngle = X_AXIS_LABEL_ANGLE
            }

            setScaleEnabled(true)
            description = Description().apply {
                text = CHART_DESCRIPTION
                textSize = CHART_TEXT_SIZE
            }
            data = LineData(listOf(LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return unitsProvider.provideWeatherValue(value.toDouble())
                    }
                }
                valueTextSize = CHART_TEXT_SIZE
                lineWidth = CHART_TEMPERATURE_LINE_WIDTH
            }))
        }
    }
}