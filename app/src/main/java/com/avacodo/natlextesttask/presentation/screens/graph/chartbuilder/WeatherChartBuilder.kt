package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData

private const val CHART_TEXT_SIZE = 12f
private const val CHART_DESCRIPTION = "Дата"

class WeatherChartBuilder(
    private val chartInitializer: ChartInitializer<WeatherGraphDataDomain>
) : ChartBuilder<WeatherGraphDataDomain> {

    override fun build(chartView: LineChart) {
        chartView.apply {
            chartInitializer.run {
                initAxisRight(axisRight)
                initAxisLeft(axisLeft)
                initXAxis(xAxis)
            }
            setScaleEnabled(true)
            description = Description().apply {
                text = CHART_DESCRIPTION
                textSize = CHART_TEXT_SIZE
            }
        }
    }

    override fun invalidate(chartView: LineChart, weatherGraphDataDomain: WeatherGraphDataDomain) {
        chartInitializer.initNewValues(weatherGraphDataDomain)

        chartView.apply {
            notifyDataSetChanged()
            data = LineData(listOf(chartInitializer.initDataSet()))
            invalidate()
        }
    }
}