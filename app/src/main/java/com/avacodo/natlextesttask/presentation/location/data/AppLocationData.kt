package com.avacodo.natlextesttask.presentation.location.data

import android.app.Activity
import com.avacodo.natlextesttask.presentation.location.permission.OnLocationCoordsReceiver

interface AppLocationData {
    fun getLocationData(activity: Activity, onReceiveLocationCallback: OnLocationCoordsReceiver)
}