package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat

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
        }
    }

    fun explain(chartView: LineChart, weatherGraphDataDomain: WeatherGraphDataDomain) {
        xAxisValuesList.clear()
        yAxisValuesList.clear()
        for(index in weatherGraphDataDomain.weatherData.indices) {
            yAxisValuesList.add(Entry(index.toFloat(), weatherGraphDataDomain.weatherData[index].temperature .toFloat()))
            xAxisValuesList.add(dateFormat.format(weatherGraphDataDomain.weatherData[index].weatherRequestTimeL))
        }
        val set1 = LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
            valueFormatter = chartValueFormatter.getDataFormatter()
            valueTextSize = CHART_TEXT_SIZE
            lineWidth = CHART_TEMPERATURE_LINE_WIDTH
        }

        chartView.notifyDataSetChanged()
        chartView.data = LineData(listOf(set1))
        chartView.invalidate()
    }
}