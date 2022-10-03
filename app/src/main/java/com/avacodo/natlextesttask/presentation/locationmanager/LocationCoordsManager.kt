package com.avacodo.natlextesttask.presentation.locationmanager

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.google.android.gms.location.LocationServices
import java.util.*

private const val PERMISSION_REQUEST_CODE = 13

class LocationCoordsManager : LocationCoordsProvider {

    private lateinit var locationCallback: OnLocationCoordsReceiver // todo: подумать про lateinit

    override fun setCallback(onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        locationCallback = onReceiveCoordsCallback
    }

    override fun checkLocationPermission(activity: AppCompatActivity) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocationCoords(activity)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE)
        }
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
                    1)

                resultList?.let {
                    val lat = (Html.fromHtml(
                        resultList[0].latitude.toString(),
                        Html.FROM_HTML_MODE_LEGACY))
                        .toString().toDouble()

                    val lon = (Html.fromHtml(
                        resultList[0].longitude.toString(),
                        Html.FROM_HTML_MODE_LEGACY))
                        .toString().toDouble()

                    locationCallback.onReceiveCoords(MyLocationCoords(lat, lon))
                }
            }
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