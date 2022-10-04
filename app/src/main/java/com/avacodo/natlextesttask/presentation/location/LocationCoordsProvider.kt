package com.avacodo.natlextesttask.presentation.location

import androidx.appcompat.app.AppCompatActivity

interface LocationCoordsProvider {
    fun setCallback(onReceiveCoordsCallback: OnLocationCoordsReceiver)
    fun checkLocationPermission(activity: AppCompatActivity)
    fun getLocationCoords(activity: AppCompatActivity)
    fun onShowRequestPermissionRationale(activity: AppCompatActivity)
    fun requestPermission(activity: AppCompatActivity)
    fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}