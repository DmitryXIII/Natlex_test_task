package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class ChartValueFormatter {
    fun getXAxisFormatter(xAxisDataList: List<String>): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return xAxisDataList[value.toInt()]
            }
        }
    }

    fun getYAxisFormatter(isCelsiusRequired: Boolean): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (isCelsiusRequired) {
                    "${(((value * 10).roundToInt()) / 10f)}° C"
                } else {
                    "${(((value * 10).roundToInt()) / 10f)}° F"
                }
            }
        }
    }

    fun getDataFormatter(): ValueFormatter {
        return object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val result = (((value * 10).roundToInt()) / 10f).toString()
                return result.substring(0, result.indexOf(".") + 2)
            }
        }
    }
}