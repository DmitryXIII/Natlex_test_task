package com.avacodo.natlextesttask.presentation.location.permission

import com.avacodo.natlextesttask.domain.entity.MyLocationCoords

fun interface OnLocationCoordsReceiver {
    fun onReceiveCoords(myLocationCoords: MyLocationCoords)
}