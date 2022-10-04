package com.avacodo.natlextesttask.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.locationmanager.LocationCoordsProvider
import com.avacodo.natlextesttask.presentation.locationmanager.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.locationmanager.LocationSettingsManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationRouter, WeatherLocationCoordsProvider {

    private val locationCoordsManager: LocationCoordsProvider by inject()
    private val locationLocationSettingsManager: LocationSettingsManager by inject()
    private lateinit var onReceiveCoordsCallback: OnLocationCoordsReceiver

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
        this.onReceiveCoordsCallback = onReceiveCoordsCallback
        locationLocationSettingsManager.checkLocationSettings(this@MainActivity, this.onReceiveCoordsCallback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        locationCoordsManager.onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                locationLocationSettingsManager.checkLocationPermission(this, onReceiveCoordsCallback)
                Log.d("@#@", "onActivityResult: RESULT OK")
            }
            Activity.RESULT_CANCELED -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Сообщение")
                    .setMessage("Включите геолокацию")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}