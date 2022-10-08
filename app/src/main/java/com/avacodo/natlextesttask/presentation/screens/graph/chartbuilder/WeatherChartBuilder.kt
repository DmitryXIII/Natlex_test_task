package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData

private const val CHART_DESCRIPTION = "Дата"
private const val CHART_TEXT_SIZE = 12f
private const val EXTRA_RIGHT_OFFSET = 20f

class WeatherChartBuilder(
    private val chartInitializer: ChartInitializer<WeatherGraphDataDomain>,
) : ChartBuilder<WeatherGraphDataDomain> {

    override fun build(
        chartView: LineChart,
        isSwitchChecked: Boolean,
        chartData: WeatherGraphDataDomain,
    ) {
        chartView.apply {
            setScaleEnabled(true)
            extraRightOffset = EXTRA_RIGHT_OFFSET
            description = Description().apply {
                text = CHART_DESCRIPTION
                textSize = CHART_TEXT_SIZE
            }

            chartInitializer.run {
                setRequiredWeatherUnits(isSwitchChecked)
                setChartData(chartData)
                initAxisLeft(axisLeft)
                initAxisRight(axisRight)
                initXAxis(xAxis)
                data = LineData(chartInitializer.initDataSet(chartView.context))
            }

            invalidate()
        }
    }
}