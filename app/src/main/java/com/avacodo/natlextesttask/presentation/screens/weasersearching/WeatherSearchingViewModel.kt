package com.avacodo.natlextesttask.presentation.screens.weasersearching

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase
import com.avacodo.natlextesttask.presentation.AppState
import com.avacodo.natlextesttask.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

private const val SWITCH_STATE_KEY = "SWITCH_STATE"

class WeatherSearchingViewModel(
    private val usecase: GetWeatherUsecase,
    private val state: SavedStateHandle,
) :
    BaseViewModel<WeatherModelDomain>() {

    private val switchCheckedState = state.getLiveData(SWITCH_STATE_KEY, true)

    fun getSwitchCheckedState(): LiveData<Boolean> = switchCheckedState

    fun saveSwitchCheckedState(switchIsChecked: Boolean) {
        state[SWITCH_STATE_KEY] = switchIsChecked
    }

    suspend fun getLocalData(): Flow<List<WeatherModelDomain>> {
        return usecase.getLocalWeather()
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