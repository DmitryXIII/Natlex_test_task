package com.avacodo.natlextesttask.presentation.locationmanager

import android.content.IntentSender
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class LocationSettingsManager(private val locationCoordsManager: LocationCoordsProvider) {
    private val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(createLocationRequestHighAccuracy())
        .addLocationRequest(createLocationRequestBalancedPowerAccuracy())
        .setNeedBle(true)

    private fun createLocationRequestHighAccuracy(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun createLocationRequestBalancedPowerAccuracy(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    fun checkLocationSettings(activity: AppCompatActivity, onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
            .addOnCompleteListener {
                try {
                    it.getResult(ApiException::class.java)
                    checkLocationPermission(activity, onReceiveCoordsCallback)
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Log.d("@#@", "RESOLUTION_REQUIRED")
                            try {
                                val resolvable = exception as ResolvableApiException
                                resolvable.startResolutionForResult(activity, 50);
                            } catch (e: IntentSender.SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        }
                    }
                }
            }
    }

    fun checkLocationPermission(activity: AppCompatActivity, onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        locationCoordsManager.setCallback(onReceiveCoordsCallback)
        locationCoordsManager.checkLocationPermission(activity)
    }
}