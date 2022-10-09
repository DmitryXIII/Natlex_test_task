package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.domain.weatherunits.WeatherUnitsProviderFactory
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.activity.WeatherLocationCoordsProvider
import com.avacodo.natlextesttask.presentation.screens.weasersearching.backgrounddrawer.BackgroundDrawerFactory
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview.SearchViewInitialise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class WeatherSearchingFragment :
    BaseFragment<FragmentWeatherSearchingBinding, WeatherModelDomain>(
        FragmentWeatherSearchingBinding::inflate) {

    override val viewModel by stateViewModel<WeatherSearchingViewModel>()
    private val searchViewInitializer: SearchViewInitialise by inject()
    override val progressBar: ProgressBar by lazy { binding.weatherSearchingProgressBar }
    private val isSwitchCheckedFlow = MutableStateFlow(true)
    private val weatherSearchingAdapter = WeatherSearchingAdapter { locationID ->
        router.navigateToTemperatureGraphScreen(locationID, binding.weatherUnitsSwitch.isChecked)
    }
    private lateinit var currentWeatherData: WeatherModelDomain
    private var weatherUnitsProvider = initWeatherValueProvider(true)
    private lateinit var queryToSave: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search_weather_by_name)
        val searchView = searchMenuItem.actionView as SearchView
        val onQueryChangeAction: (String) -> Unit = { newQuery ->
            queryToSave = newQuery
        }

        viewModel.getSearchViewSavedQuery().observe(viewLifecycleOwner) { savedQuery ->
            searchViewInitializer.setSavedQuery(searchMenuItem, searchView, savedQuery)
            queryToSave = savedQuery
        }

        searchViewInitializer.initSearchView(searchView, onQueryChangeAction) { query ->
            viewModel.searchWeather(query)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search_weather_by_coords) {
            (requireActivity() as WeatherLocationCoordsProvider).provideLocationCoords(object :
                OnLocationCoordsReceiver {
                override fun onStartLocationRequest() {
                    binding.weatherSearchingProgressBar.isVisible = true
                }

                override fun onSuccessLocationRequest(myLocationCoords: MyLocationCoords) {
                    binding.weatherSearchingProgressBar.isVisible = false
                    viewModel.searchWeather(myLocationCoords.latitude, myLocationCoords.longitude)
                }
            })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSwitchCheckedState().observe(viewLifecycleOwner) { switchCheckedState ->
            initOnViewCreatedState(switchCheckedState)
        }

        viewLifecycleOwner.lifecycleScope.launch {
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
                provideOnErrorAction,
            )
        }

        binding.weatherHistoryRecyclerView.adapter = weatherSearchingAdapter

        initSwitch()
    }

    private fun initSwitch() {
        binding.weatherUnitsSwitch.setOnCheckedChangeListener { _, isChecked ->
            isSwitchCheckedFlow.value = isChecked
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
        if (this::currentWeatherData.isInitialized) {
            binding.temperatureTextView.text = weatherUnitsProvider.provideWeatherValue(
                currentWeatherData.temperatureInCelsius
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

    override fun onDestroyView() {
        viewModel.saveSwitchCheckedState(binding.weatherUnitsSwitch.isChecked)
        viewModel.saveSearchViewSavedQuery(queryToSave)
        super.onDestroyView()
    }
}