package com.avacodo.natlextesttask.presentation.location.permission

import com.avacodo.natlextesttask.domain.entity.MyLocationCoords

interface OnLocationCoordsReceiver {
    fun onStartLocationRequest()
    fun onSuccessLocationRequest(myLocationCoords: MyLocationCoords)
}