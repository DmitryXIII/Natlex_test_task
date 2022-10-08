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
import com.avacodo.natlextesttask.presentation.screens.graph.slider.SliderInitializer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"
private const val IS_SWITCH_CHECKED_ARG_KEY = "IS_SWITCH_CHECKED"

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder by inject<ChartBuilder<WeatherGraphDataDomain>>()

    private val sliderInitializer by inject<SliderInitializer<WeatherGraphDataDomain>>()

    override val viewModel by viewModel<WeatherGraphViewModel>()

    private var isSwitchChecked = true

    private var isFragmentStateSaved = false

    private lateinit var currentLocationID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFragmentStateSaved = (savedInstanceState != null)

        arguments?.let {
            isSwitchChecked = it.getBoolean(IS_SWITCH_CHECKED_ARG_KEY)
            currentLocationID =
                it.getString(LOCATION_ID_ARG_KEY) ?: getString(R.string.argument_error)
        }

        viewModel.getData().observe(viewLifecycleOwner) {
            it.handleState(
                onStartLoadingAction = provideOnStartLoadingAction,
                onSuccessAction = provideOnSuccessAction,
                onErrorAction = provideOnErrorAction,
                onInitializationAction = provideOnInitAction
            )
        }

        if (savedInstanceState == null) {
            viewModel.onInitialization(currentLocationID)
        } else {
            sliderInitializer.initSlider(binding.weatherGraphRangeSlider) { timeFrom, timeTo ->
                viewModel.getWeatherGraphDataByRange(currentLocationID, timeFrom, timeTo)
            }
        }
    }

    override val provideOnSuccessAction: (WeatherGraphDataDomain) -> Unit = { weatherGraphData ->
        super.provideOnSuccessAction
        chartBuilder.build(binding.weatherGraphChartView, isSwitchChecked, weatherGraphData)
    }

    override val provideOnInitAction: (WeatherGraphDataDomain) -> Unit = { weatherGraphData ->
        sliderInitializer.setSliderData(
            binding.weatherGraphRangeSlider,
            weatherGraphData) { timeFrom, timeTo ->
            viewModel.getWeatherGraphDataByRange(currentLocationID, timeFrom, timeTo)
        }

        chartBuilder.build(binding.weatherGraphChartView, isSwitchChecked, weatherGraphData)
    }

    companion object {
        fun newInstance(locationID: String, isSwitchChecked: Boolean): WeatherGraphFragment {
            return WeatherGraphFragment().apply {
                arguments = bundleOf(
                    LOCATION_ID_ARG_KEY to locationID,
                    IS_SWITCH_CHECKED_ARG_KEY to isSwitchChecked)
            }
        }
    }
}