package com.avacodo.natlextesttask.presentation.screens.weasersearching.location

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.data.AppLocationData
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.AppLocationPermissionManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.settings.AppLocationSettingsManager

class NatlexAppLocationGlobalManager(
    private val locationSettingsManager: AppLocationSettingsManager,
    private val locationPermissionManager: AppLocationPermissionManager,
    private val locationDatasource: AppLocationData,
) : AppLocationGlobalManager {

    private lateinit var onReceiveLocationCallback: OnLocationCoordsReceiver

    override fun setOnReceiveLocationCallback(onReceiveLocationCallback: OnLocationCoordsReceiver) {
        this.onReceiveLocationCallback = onReceiveLocationCallback
    }

    override fun checkLocationSettings(activity: AppCompatActivity) {
        locationSettingsManager.checkIfLocationSettingsOn(activity) {
            checkLocationPermission(activity)
        }
    }

    override fun checkLocationPermission(activity: AppCompatActivity) {
        if (locationPermissionManager.checkLocationPermission(activity)) {
            getLocationData(activity)
        }
    }

    override fun getLocationData(activity: Activity) {
        locationDatasource.getLocationData(activity, onReceiveLocationCallback)
    }

    override fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        locationPermissionManager.onRequestPermissionsResult(
            activity,
            requestCode,
            permissions,
            grantResults
        ) {
            getLocationData(it)
        }
    }
}