package com.avacodo.natlextesttask.presentation.weatherunits

import com.avacodo.natlextesttask.domain.WeatherUnitsMapper

abstract class BaseUnitsProvider: WeatherUnitsProvider {
    protected val unitsMapper = WeatherUnitsMapper()
}