package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsMapper
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProviderFactory
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class WeatherChartValueFormatter : ChartValueFormatter {
    fun initWeatherUnitsProvider(isCelsiusRequired: Boolean): WeatherUnitsProvider {
        return WeatherUnitsProviderFactory().initWeatherUnitsProvider(isCelsiusRequired)
    }

    override fun getXAxisFormatter(xAxisDataList: List<String>): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return xAxisDataList[value.toInt()]
            }
        }
    }

    override fun getYAxisFormatter(isCelsiusRequired: Boolean): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return initWeatherUnitsProvider(isCelsiusRequired).provideWeatherValue(value.toDouble())
            }
        }
    }

    override fun getDataFormatter(): ValueFormatter {
        return object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val result = WeatherUnitsMapper().roundValueToOneDecimal(value.toDouble()).toString()
                return result.substring(0, result.indexOf(".") + 2)
            }
        }
    }
}