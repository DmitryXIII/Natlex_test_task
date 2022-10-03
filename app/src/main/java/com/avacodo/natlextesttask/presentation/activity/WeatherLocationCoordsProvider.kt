package com.avacodo.natlextesttask.presentation.activity

import com.avacodo.natlextesttask.presentation.locationmanager.OnLocationCoordsReceiver

interface WeatherLocationCoordsProvider {
    fun provideLocationCoords(onReceiveCoordsCallback: OnLocationCoordsReceiver)
}