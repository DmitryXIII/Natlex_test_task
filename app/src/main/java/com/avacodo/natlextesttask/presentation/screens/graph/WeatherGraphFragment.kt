package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.ChartBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherGraphDataDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val chartBuilder = ChartBuilder()

    override val viewModel by viewModel<WeatherGraphViewModel>()

    override val progressBar by lazy { binding.weatherGraphProgressBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartBuilder.build(binding.weatherGraphChartView)
        val currentLocationID =
            arguments?.getString(LOCATION_ID_ARG_KEY) ?: "Ошибка передачи аргумента"

        Log.d("@#@", "------------- $currentLocationID")

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
        chartBuilder.explain(binding.weatherGraphChartView, it)
    }

    companion object {
        private const val LOCATION_ID_ARG_KEY = "LOCATION_ID"

        fun newInstance(locationID: String): WeatherGraphFragment {
            return WeatherGraphFragment().apply {
                arguments = bundleOf(LOCATION_ID_ARG_KEY to locationID)
            }
        }
    }
}