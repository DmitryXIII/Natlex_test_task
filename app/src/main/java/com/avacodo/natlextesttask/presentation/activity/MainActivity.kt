package com.avacodo.natlextesttask.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.locationmanager.LocationCoordsProvider
import com.avacodo.natlextesttask.presentation.locationmanager.OnLocationCoordsReceiver
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationRouter, WeatherLocationCoordsProvider {

    private val locationCoordsManager: LocationCoordsProvider by inject()

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
        locationCoordsManager.run {
            setCallback(onReceiveCoordsCallback)
            checkLocationPermission(this@MainActivity)
        }
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
}