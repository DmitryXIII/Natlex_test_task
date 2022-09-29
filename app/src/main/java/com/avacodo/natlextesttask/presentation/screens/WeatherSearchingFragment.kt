package com.avacodo.natlextesttask.presentation.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherSearchingFragment :
    BaseFragment<FragmentWeatherSearchingBinding>(FragmentWeatherSearchingBinding::inflate) {

    private val viewModel by viewModel<WeatherSearchingViewModel>()
    private val isFahrenheitRequiredFlow = MutableStateFlow(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
        val searchView = menu.findItem(R.id.action_search_weather_by_name).actionView as SearchView
        searchView.queryHint = getString(R.string.search_view_hint)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.switchState.collect {
                binding.weatherUnitsSwitch.isChecked = it
                isFahrenheitRequiredFlow.value = it
            }
        }

        viewModel.getData().observe(viewLifecycleOwner) { state ->
            state.handleState(
                initLoadingAction(),
                initSuccessAction(),
                initErrorAction()
            )
        }
        viewModel.searchWeather("Петрозаводск") // временно ... для теста вьюмодели

        binding.weatherUnitsSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.changeSwitchState()
            isFahrenheitRequiredFlow.value = binding.weatherUnitsSwitch.isChecked
        }
    }

    private fun initLoadingAction(): () -> Unit = {}

    private fun initErrorAction(): (String) -> Unit = { error ->
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initSuccessAction(): (WeatherModelDomain) -> Unit =
        { weatherData ->
            binding.locationNameTextView.text = weatherData.locationName
            viewLifecycleOwner.lifecycleScope.launch {
                isFahrenheitRequiredFlow.collect { isChecked ->
                    binding.temperatureTextView.text = initWeatherValueProvider(isChecked).provideWeatherValue(requireContext(), weatherData)
                }
            }
        }

    private fun initWeatherValueProvider(isSwitchChecked: Boolean): WeatherUnitsProvider {
        return WeatherUnitsProviderFactory().initWeatherUnitsProvider(isSwitchChecked)
    }
}