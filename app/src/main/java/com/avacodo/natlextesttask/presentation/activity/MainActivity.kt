package com.avacodo.natlextesttask.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.domain.entity.MyLocationCoords
import com.avacodo.natlextesttask.presentation.screens.weasersearching.OnLocationCoordsReceive
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : AppCompatActivity(), NavigationRouter {

    private lateinit var locationCallback: OnLocationCoordsReceive

    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_navigation_container, WeatherSearchingFragment())
                .commit()
        }
    }

    override fun navigateToTemperatureGraphScreen() {
        //TODO("навигация на экран с графиком температур")
    }

    fun provideLocationCoords(callback: OnLocationCoordsReceive) {
        locationCallback = callback
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getLocationCoords()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                13)
        }
    }

    private fun getLocationCoords() {
        locationProvider.lastLocation.addOnCompleteListener {
            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val resultList = geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)

            resultList?.let {
                val lat = (Html.fromHtml(resultList[0].latitude.toString(),
                    Html.FROM_HTML_MODE_LEGACY)).toString().toDouble()
                val lon = (Html.fromHtml(resultList[0].longitude.toString(),
                    Html.FROM_HTML_MODE_LEGACY)).toString().toDouble()
                locationCallback.onReceiveCoords(MyLocationCoords(lat, lon))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 13) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationCoords()
            }
        }
    }
}