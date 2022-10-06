package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet

interface ChartInitializer<T> {
    fun initNewValues(weatherGraphDataDomain: T)
    fun initXAxis(xAxis: XAxis)
    fun initAxisLeft(yAxis: YAxis)
    fun initAxisRight(yAxis: YAxis)
    fun initDataSet(): LineDataSet
}