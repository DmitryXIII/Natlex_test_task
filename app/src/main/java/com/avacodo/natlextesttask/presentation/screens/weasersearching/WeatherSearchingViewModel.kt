package com.avacodo.natlextesttask.presentation.screens.weasersearching

import androidx.lifecycle.viewModelScope
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase
import com.avacodo.natlextesttask.presentation.AppState
import com.avacodo.natlextesttask.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WeatherSearchingViewModel(private val usecase: GetWeatherUsecase) :
    BaseViewModel<WeatherModelDomain>() {

    private var switchIsChecked = true

    val switchState = flow {
        emit(switchIsChecked)
    }

    suspend fun getLocalData(): Flow<List<WeatherModelDomain>> {
        return usecase.getLocalWeather()
    }

    fun changeSwitchState() {
        switchIsChecked = !switchIsChecked
    }

    fun searchWeather(locationName: String) {
        liveData.postValue(AppState.Loading())
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            usecase.getRemoteWeather(locationName).also { weatherData ->
                usecase.addLocalWeatherData(weatherData)
                liveData.postValue(AppState.Success(weatherData))
            }
        }
    }

    fun searchWeather(latitude: Double, longitude: Double) {
        liveData.postValue(AppState.Loading())
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            usecase.getRemoteWeather(latitude, longitude).also { weatherData ->
                usecase.addLocalWeatherData(weatherData)
                liveData.postValue(AppState.Success(weatherData))
            }
        }
    }
}