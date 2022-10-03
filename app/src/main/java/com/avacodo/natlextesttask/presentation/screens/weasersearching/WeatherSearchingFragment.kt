package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.backgrounddrawer.BackgroundDrawerFactory
import com.avacodo.natlextesttask.presentation.searchview.SearchViewInitializer
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProviderFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherSearchingFragment :
    BaseFragment<FragmentWeatherSearchingBinding, WeatherModelDomain>(
        FragmentWeatherSearchingBinding::inflate) {

    override val viewModel by viewModel<WeatherSearchingViewModel>()
    override val progressBar: ProgressBar by lazy { binding.weatherSearchingProgressBar }
    private val isSwitchCheckedFlow = MutableStateFlow(true)
    private val weatherSearchingAdapter = WeatherSearchingAdapter {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() // todo: сделать навигацию на экран с графиком
    }
    private var currentWeatherData: WeatherModelDomain? = null
    private var weatherUnitsProvider = initWeatherValueProvider(true)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_search_weather_by_coords) {
            Toast.makeText(requireContext(), "on Location click", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.switchState.collect {
                    initOnViewCreatedState(it)
                }
            }

            launch {
                viewModel.getLocalData().collect {
                    weatherSearchingAdapter.setData(it)
                    binding.weatherHistoryRecyclerView.smoothScrollToPosition(0)
                }
            }

            launch {
                isSwitchCheckedFlow.collect { isChecked ->
                    weatherUnitsProvider = initWeatherValueProvider(isChecked)
                    displayWeatherValue()
                }
            }
        }

        viewModel.getData().observe(viewLifecycleOwner) { state ->
            state.handleState(
                provideOnStartLoadingAction,
                provideOnSuccessAction,
                provideOnErrorAction
            )
        }

        binding.weatherHistoryRecyclerView.adapter = weatherSearchingAdapter

        initSwitch()
    }

    private fun initSwitch() {
        binding.weatherUnitsSwitch.setOnCheckedChangeListener { _, isChecked ->
            isSwitchCheckedFlow.value = isChecked
            viewModel.changeSwitchState()
            weatherSearchingAdapter.setWeatherUnits(weatherUnitsProvider)
        }
    }

    private fun initOnViewCreatedState(isSwitchChecked: Boolean) {
        binding.weatherUnitsSwitch.isChecked = isSwitchChecked
        isSwitchCheckedFlow.value = isSwitchChecked
        weatherUnitsProvider = initWeatherValueProvider(isSwitchChecked)
        weatherSearchingAdapter.setWeatherUnits(weatherUnitsProvider)
    }

    override val provideOnErrorAction: (String) -> Unit = { errorMessage ->
        super.provideOnErrorAction(errorMessage)
        binding.locationNameTextView.text = getString(R.string.empty_value)
        binding.temperatureTextView.text = getString(R.string.empty_value)
        setBackgroundColor(binding.mainWeatherLayout)
    }

    override val provideOnSuccessAction: (WeatherModelDomain) -> Unit = { weatherData ->
        super.provideOnSuccessAction(weatherData)
        binding.locationNameTextView.text = weatherData.locationName
        setBackgroundColor(binding.mainWeatherLayout, weatherData.temperatureInCelsius)
        currentWeatherData = weatherData
        displayWeatherValue()
    }

    private fun displayWeatherValue() {
        currentWeatherData?.let {
            binding.temperatureTextView.text = weatherUnitsProvider.provideWeatherValue(
                requireContext(),
                it
            )
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