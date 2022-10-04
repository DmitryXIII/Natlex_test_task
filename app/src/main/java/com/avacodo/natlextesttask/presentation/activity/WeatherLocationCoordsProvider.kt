package com.avacodo.natlextesttask.presentation.activity

import com.avacodo.natlextesttask.presentation.location.permission.OnLocationCoordsReceiver

interface WeatherLocationCoordsProvider {
    fun provideLocationCoords(onReceiveCoordsCallback: OnLocationCoordsReceiver)
}