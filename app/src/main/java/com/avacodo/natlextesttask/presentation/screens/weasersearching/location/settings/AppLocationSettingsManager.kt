package com.avacodo.natlextesttask.presentation.screens.weasersearching.location.settings

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationRequest

interface AppLocationSettingsManager {
    fun createLocationRequest(mPriority: Int): LocationRequest
    fun checkIfLocationSettingsOn(activity: AppCompatActivity, onLocationSettingsOn: () -> Unit)
}