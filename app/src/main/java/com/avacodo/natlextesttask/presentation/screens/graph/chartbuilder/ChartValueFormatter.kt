package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.github.mikephil.charting.formatter.ValueFormatter

interface ChartValueFormatter {
    fun getXAxisFormatter(xAxisDataList: List<String>): ValueFormatter
    fun getYAxisFormatter(): ValueFormatter
    fun getDataFormatter(): ValueFormatter
    fun initWeatherUnitsProvider(isCelsiusRequired: Boolean)
}