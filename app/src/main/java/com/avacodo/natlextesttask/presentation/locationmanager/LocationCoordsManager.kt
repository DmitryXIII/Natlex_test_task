package com.avacodo.natlextesttask.presentation.locationmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

private const val PERMISSION_REQUEST_CODE = 13

class LocationCoordsManager : LocationCoordsProvider {

    private lateinit var onReceiveLocationCallback: OnLocationCoordsReceiver // todo: подумать про lateinit

    override fun setCallback(onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        onReceiveLocationCallback = onReceiveCoordsCallback
    }

    override fun checkLocationPermission(activity: AppCompatActivity) {
        when {
            ContextCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED -> {
                getLocationCoords(activity)
            }

            activity.shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION) -> {
                onShowRequestPermissionRationale(activity)
            }

            else -> requestPermission(activity)
        }
    }

    override fun requestPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)
    }

    override fun getLocationCoords(activity: AppCompatActivity) {
        val locationClient = LocationServices
            .getFusedLocationProviderClient(activity)

        val locationRequest = LocationRequest
            .create()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationClient.removeLocationUpdates(this)
                onReceiveLocationCallback.onReceiveCoords(
                    MyLocationCoords(
                        locationResult.locations.first().latitude,
                        locationResult.locations.first().longitude
                    ))
            }
        }

        locationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onShowRequestPermissionRationale(activity: AppCompatActivity) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(activity.getString(R.string.alert_dialog_title))
            .setMessage(activity.getString(R.string.alert_dialog_message))
            .setPositiveButton(
                activity.getString(R.string.alert_dialog_positive_button)) { _, _ ->
                requestPermission(activity)
            }
            .setNegativeButton(
                activity.getString(R.string.alert_dialog_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 13) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationCoords(activity)
            }
        }
    }
}