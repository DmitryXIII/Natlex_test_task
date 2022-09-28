package com.avacodo.natlextesttask.presentation.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherSearchingFragment :
    BaseFragment<FragmentWeatherSearchingBinding>(FragmentWeatherSearchingBinding::inflate) {

    private val viewModel by viewModel<WeatherSearchingViewModel>()

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

        viewModel.getData().observe(viewLifecycleOwner) {
            it.handleState(
                initLoadingAction(),
                initSuccessAction(),
                initErrorAction()
            )
        }

        viewModel.searchWeather("Петрозаводск") // временно ... для теста вьюмодели
    }

    private fun initLoadingAction(): () -> Unit = {}
    private fun initErrorAction(): (String) -> Unit = { error ->
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initSuccessAction(): (WeatherModelDomain) -> Unit = {
        binding.locationNameTextView.text = it.locationName
        binding.temperatureTextView.text = it.temperatureInCelsius.toString()
    }
}