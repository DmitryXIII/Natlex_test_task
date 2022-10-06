package com.avacodo.natlextesttask.presentation.activity

import androidx.fragment.app.Fragment

interface NavigationRouter {
    fun navigateToTemperatureGraphScreen(locationID: String)
    fun navigateToDestination(destination: Fragment, isBackstackRequired: Boolean = false)
}