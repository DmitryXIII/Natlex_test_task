package com.avacodo.natlextesttask.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithoutNegativeButton
import com.avacodo.natlextesttask.presentation.location.AppLocationGlobalManager
import com.avacodo.natlextesttask.presentation.location.permission.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.screens.graph.WeatherGraphFragment
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import org.koin.android.ext.android.inject

private const val NAVIGATION_BACKSTACK = "NAVIGATION_BACKSTACK"

class MainActivity : AppCompatActivity(), NavigationRouter, WeatherLocationCoordsProvider {

    private val locationManager: AppLocationGlobalManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            navigateToDestination(WeatherSearchingFragment())
        }
    }

    override fun navigateToTemperatureGraphScreen(locationID: String) {
        navigateToDestination(WeatherGraphFragment.newInstance(locationID), true)
    }

    override fun navigateToDestination(destination: Fragment, isBackstackRequired: Boolean) {
        supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.main_navigation_container, destination)
                if (isBackstackRequired) {
                    addToBackStack(NAVIGATION_BACKSTACK)
                }
                commit()
            }
    }

    override fun provideLocationCoords(onReceiveCoordsCallback: OnLocationCoordsReceiver) {
        locationManager.run {
            setOnReceiveLocationCallback(onReceiveCoordsCallback)
            checkLocationSettings(this@MainActivity)
        }
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