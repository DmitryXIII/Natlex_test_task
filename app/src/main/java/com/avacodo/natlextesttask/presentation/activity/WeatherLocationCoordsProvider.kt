package com.avacodo.natlextesttask.presentation.activity

import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver

interface WeatherLocationCoordsProvider {
    fun provideLocationCoords(onReceiveCoordsCallback: OnLocationCoordsReceiver)
}