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
import kotlinx.coroutines.launch

private const val SWITCH_STATE_KEY = "SWITCH_STATE"
private const val SAVED_QUERY_KEY = "SAVED_QUERY"

class WeatherSearchingViewModel(
    private val usecase: GetWeatherUsecase,
    private val state: SavedStateHandle,
) :
    BaseViewModel<WeatherModelDomain>() {

    private val switchCheckedState = state.getLiveData(SWITCH_STATE_KEY, true)
    private val searchViewSavedQuery = state.getLiveData(SAVED_QUERY_KEY, "")

    fun getSwitchCheckedState(): LiveData<Boolean> = switchCheckedState

    fun getSearchViewSavedQuery(): LiveData<String> = searchViewSavedQuery

    fun saveSwitchCheckedState(switchIsChecked: Boolean) {
        state[SWITCH_STATE_KEY] = switchIsChecked
    }

    fun saveSearchViewSavedQuery(queryToSave: String) {
        state[SAVED_QUERY_KEY] = queryToSave
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