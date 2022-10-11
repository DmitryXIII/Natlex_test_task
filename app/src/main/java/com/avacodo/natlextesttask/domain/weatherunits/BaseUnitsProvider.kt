package com.avacodo.natlextesttask.domain.weatherunits

abstract class BaseUnitsProvider: WeatherUnitsProvider {
    protected val unitsMapper = WeatherUnitsMapper()
}