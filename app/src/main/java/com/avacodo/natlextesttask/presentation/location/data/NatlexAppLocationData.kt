package com.avacodo.natlextesttask.presentation.location.data

import android.app.Activity
import android.os.Looper
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.avacodo.natlextesttask.presentation.location.permission.OnLocationCoordsReceiver
import com.google.android.gms.location.*

class NatlexAppLocationData: AppLocationData {
    override fun getLocationData(
        activity: Activity,
        onReceiveLocationCallback: OnLocationCoordsReceiver,
    ) {
        onReceiveLocationCallback.onStartLocationRequest()

        val locationClient = LocationServices
            .getFusedLocationProviderClient(activity)

        //todo: проверить, нужен ли отдельный реквест
        val locationRequest = LocationRequest
            .create()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationClient.removeLocationUpdates(this)
                onReceiveLocationCallback.onSuccessLocationRequest(
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
}