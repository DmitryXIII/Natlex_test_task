package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.backgrounddrawer.BackgroundDrawerFactory
import com.avacodo.natlextesttask.presentation.searchview.SearchViewInitializer
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
        SearchViewInitializer().initSearchView(menu, R.id.action_search_weather_by_name) { query ->
            viewModel.searchWeather(query)
        }
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

        binding.weatherUnitsSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.changeSwitchState()
            isFahrenheitRequiredFlow.value = binding.weatherUnitsSwitch.isChecked
        }
    }

    private fun initLoadingAction(): () -> Unit = {}

    private fun initErrorAction(): (String) -> Unit = { error ->
        binding.locationNameTextView.text = getString(R.string.empty_value)
        binding.temperatureTextView.text = getString(R.string.empty_value)

        setBackgroundColor(binding.mainWeatherLayout)

        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initSuccessAction(): (WeatherModelDomain) -> Unit =
        { weatherData ->
            binding.locationNameTextView.text = weatherData.locationName

            setBackgroundColor(binding.mainWeatherLayout, weatherData.temperatureInCelsius)

            viewLifecycleOwner.lifecycleScope.launch {
                isFahrenheitRequiredFlow.collect { isChecked ->
                    binding.temperatureTextView.text =
                        initWeatherValueProvider(isChecked).provideWeatherValue(requireContext(),
                            weatherData)
                }
            }
        }

    private fun initWeatherValueProvider(isSwitchChecked: Boolean): WeatherUnitsProvider {
        return WeatherUnitsProviderFactory().initWeatherUnitsProvider(isSwitchChecked)
    }

    private fun setBackgroundColor(view: View, temperature: Double) {
        BackgroundDrawerFactory()
            .provideBackgroundDrawer(temperature)
            .setLayoutBackground(view)
    }

    private fun setBackgroundColor(view: View) {
        BackgroundDrawerFactory()
            .provideBackgroundDrawer()
            .setLayoutBackground(view)
    }
}