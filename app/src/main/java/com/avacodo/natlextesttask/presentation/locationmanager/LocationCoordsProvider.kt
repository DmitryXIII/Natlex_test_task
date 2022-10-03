package com.avacodo.natlextesttask.presentation.locationmanager

import androidx.appcompat.app.AppCompatActivity

interface LocationCoordsProvider {
    fun setCallback(callback: OnLocationCoordsReceiver)
    fun checkLocationPermission(activity: AppCompatActivity)
    fun getLocationCoords(activity: AppCompatActivity)
    fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}