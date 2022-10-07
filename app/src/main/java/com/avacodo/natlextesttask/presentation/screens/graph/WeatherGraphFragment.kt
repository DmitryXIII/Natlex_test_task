package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProviderFactory
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat

private const val CHART_DESCRIPTION = "Дата"
private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm"
private const val CHART_TEMPERATURE_LINE_LABEL = "Температура"
private const val CHART_TEMPERATURE_LINE_WIDTH = 2f
private const val X_AXIS_LABEL_ANGLE = 270f
private const val X_AXIS_GRANULARITY = 1f
private const val CHART_TEXT_SIZE = 12f
private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"
private const val IS_CELSIUS_REQUIRED_ARG_KEY = "IS_CELSIUS_REQUIRED"

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder by inject<ChartBuilder<WeatherGraphDataDomain>>()
    private var unitsProvider: WeatherUnitsProvider =
        WeatherUnitsProviderFactory().initWeatherUnitsProvider(true)
    override val viewModel by viewModel<WeatherGraphViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentLocationID =
            arguments?.getString(LOCATION_ID_ARG_KEY) ?: getString(R.string.argument_error)

        val isCelsiusRequired =
            arguments?.getBoolean(IS_CELSIUS_REQUIRED_ARG_KEY) ?: true


        viewModel.getData().observe(viewLifecycleOwner) {
            it.handleState(
                onStartLoadingAction = provideOnStartLoadingAction,
                onSuccessAction = provideOnSuccessAction,
                onErrorAction = provideOnErrorAction,
                onInitializationAction = provideOnInitAction
            )
        }

        viewModel.onInitialization(currentLocationID)

//        viewModel.getWeatherGraphDataByRange(
//            locationID = "509820",
//            timeFrom = 1664769840000,
//            timeTo = 1664803380000
//        )

        binding.graphButton.setOnClickListener {
            viewModel.getWeatherGraphDataByRange(
                locationID = "509820",
                timeFrom = 1664769840000,
                timeTo = 1664803380000
            )
        }
    }

    override val provideOnSuccessAction: (WeatherGraphDataDomain) -> Unit = { weatherGraphData ->
        super.provideOnSuccessAction
        build(weatherGraphData)
        Log.d("@#@", "FILTERED_DATA = $weatherGraphData")
    }

    override val provideOnInitAction: (WeatherGraphDataDomain) -> Unit = { weatherGraphData ->
        val requestTimesList = mutableListOf<Long>()
        val startTime = weatherGraphData.minRequestTime / 60000 * 60000
        val endTime = weatherGraphData.maxRequestTime / 60000 * 60000
        val minutesCount = ((endTime - startTime) / 60000).toInt() + 1
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        Log.d("@#@", "MINUTES_COUNT = $minutesCount")

        for (i in 0..minutesCount) {
//            Log.d("@#@", "INDEX = $i")
            requestTimesList.add(startTime + i * 60000L)
        }

        Log.d("@#@", "FIRST_TIME = ${requestTimesList.first()}")
        Log.d("@#@", "LAST_TIME = ${requestTimesList.last()}")

        binding.weatherGraphRangeSlider.apply {
            values = listOf(0f, minutesCount.toFloat())
            valueFrom = values.first()
            valueTo = values.last()
            stepSize = 1f
            setMinSeparationValue(1f)

            setLabelFormatter { value: Float ->
                dateFormat.format(requestTimesList[value.toInt()])
            }

            addOnChangeListener { slider, _, _ ->
                viewModel.getWeatherGraphDataByRange(
                    locationID = weatherGraphData.weatherData.first().locationID,
                    timeFrom = requestTimesList[values.first().toInt()],
                    timeTo = requestTimesList[values.last().toInt()]
                )
                Log.d("@#@", "VALUE = ${slider.values.first()} -- ${slider.values.last()} ")
            }

        }
        build(weatherGraphData)
//        chartBuilder.invalidate(binding.weatherGraphChartView, weatherGraphData)
    }

    companion object {
        fun newInstance(locationID: String, isCelsiusRequired: Boolean): WeatherGraphFragment {
            return WeatherGraphFragment().apply {
                arguments = bundleOf(
                    LOCATION_ID_ARG_KEY to locationID,
                    IS_CELSIUS_REQUIRED_ARG_KEY to isCelsiusRequired)
            }
        }
    }

    fun build(
        weatherGraphDataDomain: WeatherGraphDataDomain,
    ) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
//        val xAxisValuesList = mutableListOf<String>()
        //        val yAxisValuesList = mutableListOf<Entry>()

        val xAxisValuesList = weatherGraphDataDomain.weatherData.map {graphEntryDomain ->
            dateFormat.format(graphEntryDomain.weatherRequestTime)
        }

        val yAxisValuesList = weatherGraphDataDomain.weatherData.mapIndexed { index, graphEntryDomain ->
            Entry(index.toFloat(), graphEntryDomain.temperature.toFloat())
        }

//        for (index in weatherGraphDataDomain.weatherData.indices) {
//            yAxisValuesList.add(Entry(index.toFloat(), weatherGraphDataDomain.weatherData[index].temperature.toFloat()))
//
//            xAxisValuesList.add(
//                dateFormat.format(weatherGraphDataDomain.weatherData[index].weatherRequestTime))
//        }

        val dataSet = LineDataSet(yAxisValuesList, CHART_TEMPERATURE_LINE_LABEL).apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return unitsProvider.provideWeatherValue(value.toDouble())
                }
            }
            valueTextSize = CHART_TEXT_SIZE
            lineWidth = CHART_TEMPERATURE_LINE_WIDTH
        }

        binding.weatherGraphChartView.apply {

//            axisLeft.apply {
//                setDrawGridLines(false)
//                valueFormatter = object : ValueFormatter() {
//                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//                        return unitsProvider.provideWeatherValue(value.toDouble())
//                    }
//                }
//            }
//
//            axisRight.isEnabled = false
//
//            xAxis.apply {
//                setDrawGridLines(false)
//                valueFormatter = object : ValueFormatter() {
//                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//                        return xAxisValuesList[value.toInt()]
//                    }
//                }
//                position = XAxis.XAxisPosition.BOTTOM
//                granularity = X_AXIS_GRANULARITY
//                labelRotationAngle = X_AXIS_LABEL_ANGLE
//            }

            setScaleEnabled(true)

            description = Description().apply {
                text = CHART_DESCRIPTION
                textSize = CHART_TEXT_SIZE
            }

            data = LineData(dataSet)
            invalidate()
        }
    }
}