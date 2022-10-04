package com.avacodo.natlextesttask.presentation.location.permission

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

interface AppLocationPermissionManager {
    fun checkLocationPermission(activity: AppCompatActivity): Boolean
    fun onShowRequestPermissionRationale(activity: AppCompatActivity)
    fun requestPermission(activity: AppCompatActivity)
    fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGrantedAction: (Activity) -> Unit
    )
}