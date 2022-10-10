package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import android.content.Context
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

interface ChartInitializer {
    fun setRequiredWeatherUnits(isSwitchChecked: Boolean)
    fun initXAxis(xAxis: XAxis, xAxisValuesList: List<String>)
    fun initAxisLeft(yAxis: YAxis)
    fun initAxisRight(yAxis: YAxis)
    fun initDataSet(context: Context, yAxisValuesList: List<Entry>): LineDataSet
}