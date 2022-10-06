package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class WeatherChartValueFormatter : ChartValueFormatter {
    override fun getXAxisFormatter(xAxisDataList: List<String>): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return xAxisDataList[value.toInt()]
            }
        }
    }

    override fun getYAxisFormatter(isCelsiusRequired: Boolean): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (isCelsiusRequired) {
                    "${roundValueToOneDecimal(value)}° C"
                } else {
                    "${roundValueToOneDecimal(value)}° F"
                }
            }
        }
    }

    override fun getDataFormatter(): ValueFormatter {
        return object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val result = roundValueToOneDecimal(value).toString()
                return result.substring(0, result.indexOf(".") + 2)
            }
        }
    }

    override fun roundValueToOneDecimal(value: Float): Float {
        return ((value * 10).roundToInt()) / 10f
    }
}