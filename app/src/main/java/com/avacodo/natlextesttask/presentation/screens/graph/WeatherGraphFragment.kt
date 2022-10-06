package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.view.View
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.ChartBuilder
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherModelDomain>(
    FragmentWeatherGraphBinding::inflate) {

    override val viewModel by viewModel<WeatherSearchingViewModel>()

    override val progressBar by lazy { binding.weatherGraphProgressBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ChartBuilder().build(binding.weatherGraphChartView)
    }
}