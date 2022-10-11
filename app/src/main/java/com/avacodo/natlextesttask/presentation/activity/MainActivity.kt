package com.avacodo.natlextesttask.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithoutNegativeButton
import com.avacodo.natlextesttask.presentation.network.ConnectionHandler
import com.avacodo.natlextesttask.presentation.network.ConnectivityObserver
import com.avacodo.natlextesttask.presentation.screens.graph.WeatherGraphFragment
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.AppLocationGlobalManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.OnLocationCoordsReceiver
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Архитектура SingleActivity.
 * MainActivity, как единственная активити, выполняет роль навигатора по экранам-фрагментам и
 * организует связь с системными классами для получения координат, проверки необходимых настроек
 * устройства и наличия соединения с интернетом.
 * Openweathermap API KEY убран в BuildConfig через файл apiKey.properties (выложен в git
 * для удобства проверки работоспособности проекта).
 */

private const val NAVIGATION_BACKSTACK = "NAVIGATION_BACKSTACK"

class MainActivity : AppCompatActivity(), NavigationRouter, WeatherLocationCoordsProvider {

    private val locationManager: AppLocationGlobalManager by inject()
    private val networkStatusHandler: ConnectionHandler by inject()
    private val connectivityManager: ConnectivityObserver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (!connectivityManager.checkIfNetworkAvailable()) {
            networkStatusHandler.showNoConnectionAlert(this)
        }

        lifecycleScope.launch {
            connectivityManager.observe().collect {
                networkStatusHandler.handleInternetConnectionStatus(this@MainActivity, it)
            }
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            navigateToDestination(WeatherSearchingFragment())
        }
    }

    override fun navigateToTemperatureGraphScreen(locationID: String, isCelsiusRequired: Boolean) {
        navigateToDestination(WeatherGraphFragment.newInstance(
            locationID,
            isCelsiusRequired),
            true)
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