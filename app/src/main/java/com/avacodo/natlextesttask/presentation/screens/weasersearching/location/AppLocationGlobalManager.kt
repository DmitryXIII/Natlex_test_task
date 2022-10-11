package com.avacodo.natlextesttask.presentation.screens.weasersearching.location

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver

interface AppLocationGlobalManager {
    fun checkLocationSettings(activity: AppCompatActivity)
    fun checkLocationPermission(activity: AppCompatActivity)
    fun getLocationData(activity: Activity)
    fun setOnReceiveLocationCallback(onReceiveLocationCallback: OnLocationCoordsReceiver)
    fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    )
}