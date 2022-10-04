package com.avacodo.natlextesttask.presentation.locationmanager

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

private const val PERMISSION_REQUEST_CODE = 13
private const val MAX_GEOCODER_RESULT = 1

class LocationCoordsManager : LocationCoordsProvider {

    private lateinit var locationCallback: OnLocationCoordsReceiver // todo: подумать про lateinit

    override fun setCallback(onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        locationCallback = onReceiveCoordsCallback
    }

    override fun checkLocationPermission(activity: AppCompatActivity) {
        activity.run {
            when {
                ContextCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocationCoords(this)
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    onShowRequestPermissionRationale(this)
                }

                else -> requestPermission(this)
            }
        }
    }

    override fun requestPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)
    }

    override fun getLocationCoords(activity: AppCompatActivity) {
        LocationServices
            .getFusedLocationProviderClient(activity)
            .lastLocation
            .addOnCompleteListener {
                val geocoder = Geocoder(activity, Locale.getDefault())
                val resultList = geocoder.getFromLocation(
                    it.result.latitude,
                    it.result.longitude,
                    MAX_GEOCODER_RESULT)

                resultList?.let {
                    val lat = (Html.fromHtml(
                        resultList.first().latitude.toString(),
                        Html.FROM_HTML_MODE_LEGACY))
                        .toString().toDouble()

                    val lon = (Html.fromHtml(
                        resultList.first().longitude.toString(),
                        Html.FROM_HTML_MODE_LEGACY))
                        .toString().toDouble()

                    locationCallback.onReceiveCoords(MyLocationCoords(lat, lon))
                }
            }
    }

    override fun onShowRequestPermissionRationale(activity: AppCompatActivity) {
        MaterialAlertDialogBuilder(activity)
            .setTitle("Заголовок")
            .setMessage("Убедительное сообщение")
            .setPositiveButton("Разрешить") { _, _ ->
                requestPermission(activity)
            }
            .setNegativeButton("Запретить") { dialog, _ ->
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