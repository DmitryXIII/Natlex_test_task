package com.avacodo.natlextesttask.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithoutNegativeButton
import com.avacodo.natlextesttask.presentation.location.AppLocationGlobalManager
import com.avacodo.natlextesttask.presentation.location.permission.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationRouter, WeatherLocationCoordsProvider {

    private val locationManager: AppLocationGlobalManager by inject()

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

    override fun provideLocationCoords(onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        locationManager.setOnReceiveLocationCallback(onReceiveCoordsCallback)
        locationManager.checkLocationSettings(this@MainActivity)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        locationManager.onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                locationManager.checkLocationPermission(this)
                Log.d("@#@", "onActivityResult: RESULT OK")
            }
            Activity.RESULT_CANCELED -> {
                showAlertDialogWithoutNegativeButton(
                    R.string.default_alert_title,
                    R.string.alert_message_if_location_is_off
                ) { it.dismiss() }
            }
        }
    }
}