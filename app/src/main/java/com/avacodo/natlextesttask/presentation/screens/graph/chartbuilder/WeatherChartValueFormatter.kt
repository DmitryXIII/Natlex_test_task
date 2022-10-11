package com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder

import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProviderFactory
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class WeatherChartValueFormatter : ChartValueFormatter {
    private lateinit var unitsProvider: WeatherUnitsProvider

    override fun initWeatherUnitsProvider(isSwitchChecked: Boolean) {
        unitsProvider = WeatherUnitsProviderFactory().initWeatherUnitsProvider(isSwitchChecked)
    }

    override fun getXAxisFormatter(xAxisValuesList: List<String>): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                val index = value.toInt()
                return if (index < xAxisValuesList.size && index >= 0) {
                    xAxisValuesList[index]
                } else {
                    ""
                }
            }
        }
    }

    override fun getYAxisFormatter(): ValueFormatter {
        return object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return unitsProvider.provideWeatherValue(value.toDouble())
            }
        }
    }

    override fun getDataFormatter(): ValueFormatter {
        return object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return unitsProvider.provideWeatherValue(value.toDouble())
            }
        }
    }
}