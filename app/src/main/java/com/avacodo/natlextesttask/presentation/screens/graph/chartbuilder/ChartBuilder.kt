package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.github.mikephil.charting.charts.LineChart

interface ChartBuilder<T> {
    fun build(chartView: LineChart)
    fun invalidate(chartView: LineChart, weatherGraphDataDomain: T)
}