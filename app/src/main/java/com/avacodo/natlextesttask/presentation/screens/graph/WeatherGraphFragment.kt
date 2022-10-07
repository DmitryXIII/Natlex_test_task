package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.ChartBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"
private const val IS_CELSIUS_REQUIRED_ARG_KEY = "IS_CELSIUS_REQUIRED"

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder by inject<ChartBuilder<WeatherGraphDataDomain>>()

    override val viewModel by viewModel<WeatherGraphViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentLocationID =
            arguments?.getString(LOCATION_ID_ARG_KEY) ?: getString(R.string.argument_error)

        val isCelsiusRequired =
            arguments?.getBoolean(IS_CELSIUS_REQUIRED_ARG_KEY) ?: true

        chartBuilder.build(binding.weatherGraphChartView, isCelsiusRequired)

        viewModel.getData().observe(viewLifecycleOwner) {
            it.handleState(
                onStartLoadingAction = provideOnStartLoadingAction,
                onSuccessAction = provideOnSuccessAction,
                onErrorAction = provideOnErrorAction,
            )
        }

        viewModel.getWeatherGraphData(currentLocationID)
    }

    override val provideOnSuccessAction: (WeatherGraphDataDomain) -> Unit = {
        super.provideOnSuccessAction
        chartBuilder.invalidate(binding.weatherGraphChartView, it)
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