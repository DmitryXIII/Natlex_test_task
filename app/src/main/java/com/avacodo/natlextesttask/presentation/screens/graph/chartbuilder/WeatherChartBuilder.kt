package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.entity.GraphDataDomain
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData

private const val CHART_DESCRIPTION = "Дата"
private const val CHART_TEXT_SIZE = 12f
private const val EXTRA_RIGHT_OFFSET = 20f

class WeatherChartBuilder(
    private val chartInitializer: ChartInitializer,
) : ChartBuilder<GraphDataDomain> {

    override fun build(
        chartView: LineChart,
        isSwitchChecked: Boolean,
        chartData: GraphDataDomain,
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
                initAxisLeft(axisLeft)
                initAxisRight(axisRight)
                initXAxis(xAxis, chartData.xAxisValuesList)
                data = LineData(chartInitializer.initDataSet(chartView.context,
                    chartData.yAxisValuesList))
            }

            invalidate()
        }
    }
}