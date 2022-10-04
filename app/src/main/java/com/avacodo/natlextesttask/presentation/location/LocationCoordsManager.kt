package com.avacodo.natlextesttask.presentation.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithNegativeButton
import com.google.android.gms.location.*

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
        with(activity) {
            showAlertDialogWithNegativeButton(
                R.string.alert_dialog_title_permission_rationale,
                R.string.alert_dialog_message_permission_rationale,
                R.string.alert_dialog_positive_button_permission_rationale,
                R.string.alert_dialog_negative_button_permission_rationale,
            ) { requestPermission(this) }
        }
    }

    override fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first()
                == PackageManager.PERMISSION_GRANTED
            ) {
                getLocationCoords(activity)
            }
        }
    }
}