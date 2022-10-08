package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import android.content.Context
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet

interface ChartInitializer<T> {
    fun setRequiredWeatherUnits(isSwitchChecked: Boolean)
    fun setChartData(chartData: T)
    fun initXAxis(xAxis: XAxis)
    fun initAxisLeft(yAxis: YAxis)
    fun initAxisRight(yAxis: YAxis)
    fun initDataSet(context: Context): LineDataSet
}