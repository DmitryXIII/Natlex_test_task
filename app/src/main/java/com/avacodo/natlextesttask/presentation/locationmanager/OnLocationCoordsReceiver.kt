package com.avacodo.natlextesttask.presentation.locationmanager

import com.avacodo.natlextesttask.domain.entity.MyLocationCoords

fun interface OnLocationCoordsReceiver {
    fun onReceiveCoords(myLocationCoords: MyLocationCoords)
}