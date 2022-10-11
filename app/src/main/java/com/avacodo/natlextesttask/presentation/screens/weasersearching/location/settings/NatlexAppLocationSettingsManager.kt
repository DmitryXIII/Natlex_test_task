package com.avacodo.natlextesttask.presentation.screens.weasersearching.location.settings

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithoutNegativeButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

private const val SETTINGS_REQUEST_CODE = 50
private const val DEFAULT_REQUEST_INTERVAL = 10000L
private const val DEFAULT_REQUEST_FAST_INTERVAL = 5000L

class NatlexAppLocationSettingsManager(): AppLocationSettingsManager {

    private val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(createLocationRequest(Priority.PRIORITY_HIGH_ACCURACY))
        .addLocationRequest(createLocationRequest(Priority.PRIORITY_BALANCED_POWER_ACCURACY))
        .setNeedBle(true)

    override fun createLocationRequest(mPriority: Int): LocationRequest {
        return LocationRequest.create().apply {
            interval = DEFAULT_REQUEST_INTERVAL
            fastestInterval = DEFAULT_REQUEST_FAST_INTERVAL
            priority = mPriority
        }
    }

    override fun checkIfLocationSettingsOn(
        activity: AppCompatActivity,
        onLocationSettingsOn: () -> Unit,
    ) {
        LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
            .addOnCompleteListener {
                try {
                    it.getResult(ApiException::class.java)
                    onLocationSettingsOn.invoke()
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Log.d("@#@", "RESOLUTION_REQUIRED")
                            val resolvable = exception as ResolvableApiException
                            resolvable.startResolutionForResult(activity, SETTINGS_REQUEST_CODE)
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            activity.showAlertDialogWithoutNegativeButton(
                                R.string.default_alert_title,
                                R.string.unable_to_turn_location_on,
                                R.string.default_alert_positive_button
                            ) { dialog -> dialog.dismiss() }
                        }
                    }
                }
            }
    }
}