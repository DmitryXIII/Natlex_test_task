package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.ChartBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"
private const val IS_CELSIUS_REQUIRED_ARG_KEY = "IS_CELSIUS_REQUIRED"

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder by inject<ChartBuilder<WeatherGraphDataDomain>>()

    override val viewModel by viewModel<WeatherGraphViewModel>()

    private var isCelsiusRequired = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentLocationID =
            arguments?.getString(LOCATION_ID_ARG_KEY) ?: getString(R.string.argument_error)

        arguments?.let {
            isCelsiusRequired = it.getBoolean(IS_CELSIUS_REQUIRED_ARG_KEY)
        }

        viewModel.getData().observe(viewLifecycleOwner) {
            it.handleState(
                onStartLoadingAction = provideOnStartLoadingAction,
                onSuccessAction = provideOnSuccessAction,
                onErrorAction = provideOnErrorAction,
                onInitializationAction = provideOnInitAction
            )
        }

        viewModel.onInitialization(currentLocationID)

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
        chartBuilder.build(binding.weatherGraphChartView, isCelsiusRequired, weatherGraphData)
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
        chartBuilder.build(binding.weatherGraphChartView, isCelsiusRequired, weatherGraphData)
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
}