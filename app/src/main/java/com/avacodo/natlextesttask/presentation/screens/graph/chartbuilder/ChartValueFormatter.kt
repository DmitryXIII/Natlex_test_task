package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.github.mikephil.charting.formatter.ValueFormatter

interface ChartValueFormatter {
    fun getXAxisFormatter(xAxisDataList: List<String>): ValueFormatter
    fun getYAxisFormatter(isCelsiusRequired: Boolean): ValueFormatter
    fun getDataFormatter(): ValueFormatter
    fun roundValueToOneDecimal(value: Float): Float
}