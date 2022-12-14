package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.GraphDataDomain
import com.avacodo.natlextesttask.domain.entity.SliderDataDomain
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.ChartBuilder
import com.avacodo.natlextesttask.presentation.screens.graph.slider.SliderInitializer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"
private const val IS_SWITCH_CHECKED_ARG_KEY = "IS_SWITCH_CHECKED"

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder by inject<ChartBuilder<GraphDataDomain>>()

    private val sliderInitializer by inject<SliderInitializer<SliderDataDomain>>()

    override val viewModel by stateViewModel<WeatherGraphViewModel>()

    private var isSwitchChecked = true

    private lateinit var currentLocationID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            )
        }

        if (savedInstanceState == null) {
            viewModel.saveSliderIsInitializedState(false)
            viewModel.onInitialization(currentLocationID)
        }

        sliderInitializer.initSlider(binding.weatherGraphRangeSlider) { timeFrom, timeTo, hintTimeFrom, hintTimeTo ->
            viewModel.getWeatherGraphDataByRange(currentLocationID, timeFrom, timeTo)
            binding.filterDataTextView.text = getString(R.string.filter_data_hint, hintTimeFrom, hintTimeTo)
        }
    }

    override val provideOnSuccessAction: (WeatherGraphDataDomain) -> Unit = { weatherGraphData ->
        super.provideOnSuccessAction
        viewModel.getSliderIsInitializedState()
            .observe(viewLifecycleOwner) { sliderIsInitializedState ->
                if (!sliderIsInitializedState) {
                    sliderInitializer.setSliderData(
                        binding.weatherGraphRangeSlider,
                        weatherGraphData.sliderData)
                    viewModel.saveSliderIsInitializedState(true)
                }
            }
        chartBuilder.build(binding.weatherGraphChartView, isSwitchChecked, weatherGraphData.graphData)
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