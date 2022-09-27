package com.avacodo.natlextesttask.presentation.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingBinding
import com.avacodo.natlextesttask.presentation.BaseFragment

class WeatherSearchingFragment :

    BaseFragment<FragmentWeatherSearchingBinding>(FragmentWeatherSearchingBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
        val searchView = menu.findItem(R.id.action_search_weather_by_name).actionView as SearchView
        searchView.queryHint = "Введите название города"
        super.onCreateOptionsMenu(menu, inflater)
    }
}