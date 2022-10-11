package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import android.content.Context
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

private const val CHART_TEMPERATURE_LINE_LABEL = "Температура"
private const val CHART_TEMPERATURE_LINE_WIDTH = 2f
private const val X_AXIS_LABEL_ANGLE = 270f
private const val X_AXIS_GRANULARITY = 1f
private const val CHART_TEXT_SIZE = 12f

class WeatherChartInitializer(
    private val chartValueFormatter: ChartValueFormatter,
) : ChartInitializer {

    override fun setRequiredWeatherUnits(isSwitchChecked: Boolean) {
        chartValueFormatter.initWeatherUnitsProvider(isSwitchChecked)
    }

    override fun initXAxis(xAxis: XAxis, xAxisValuesList: List<String>) {
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

    override fun initDataSet(context: Context, yAxisValuesList: List<Entry>): LineDataSet {
        return LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
            setDrawFilled(true)
            valueFormatter = chartValueFormatter.getDataFormatter()
            valueTextSize = CHART_TEXT_SIZE
            lineWidth = CHART_TEMPERATURE_LINE_WIDTH
        }
    }
}