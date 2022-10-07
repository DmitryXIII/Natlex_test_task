package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.github.mikephil.charting.charts.LineChart

interface ChartBuilder<T> {
    fun build(chartView: LineChart, isCelsiusRequired: Boolean, weatherGraphDataDomain: WeatherGraphDataDomain)
//    fun invalidate(chartView: LineChart, weatherGraphDataDomain: T)
}