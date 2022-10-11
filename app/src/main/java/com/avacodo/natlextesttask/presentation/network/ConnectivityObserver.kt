package com.avacodo.natlextesttask.presentation.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun checkIfNetworkAvailable(): Boolean
    fun observe(): Flow<NetworkConnectionStatus>
}