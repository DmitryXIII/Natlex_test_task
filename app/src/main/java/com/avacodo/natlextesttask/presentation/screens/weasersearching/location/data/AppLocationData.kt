package com.avacodo.natlextesttask.presentation.screens.weasersearching.location.data

import android.app.Activity
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver

interface AppLocationData {
    fun getLocationData(activity: Activity, onReceiveLocationCallback: OnLocationCoordsReceiver)
}