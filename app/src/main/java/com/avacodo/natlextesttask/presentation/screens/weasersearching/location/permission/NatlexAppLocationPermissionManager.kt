package com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithNegativeButton

private const val PERMISSION_REQUEST_CODE = 13

class NatlexAppLocationPermissionManager : AppLocationPermissionManager {

    override fun checkLocationPermission(activity: AppCompatActivity): Boolean {
        return when {
            ContextCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED -> {
                true
            }

            activity.shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION) -> {
                onShowRequestPermissionRationale(activity)
                false
            }

            else -> {
                requestPermission(activity)
                false
            }
        }
    }

    override fun requestPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)
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
        onPermissionGrantedAction: (Activity) -> Unit
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first()
                == PackageManager.PERMISSION_GRANTED
            ) {
                onPermissionGrantedAction.invoke(activity)
            }
        }
    }
}