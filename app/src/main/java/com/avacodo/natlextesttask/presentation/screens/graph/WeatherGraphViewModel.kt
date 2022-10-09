package com.avacodo.natlextesttask.presentation.screens.graph

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase
import com.avacodo.natlextesttask.presentation.AppState
import com.avacodo.natlextesttask.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val SLIDER_IS_INITIALIZED_STATE_KEY = "SLIDER_IS_INITIALIZED_STATE"

class WeatherGraphViewModel(private val usecase: WeatherGraphUsecase, private val state: SavedStateHandle) :
    BaseViewModel<WeatherGraphDataDomain>() {

    private val sliderIsInitializedState = state.getLiveData(SLIDER_IS_INITIALIZED_STATE_KEY, false)

    fun getSliderIsInitializedState(): LiveData<Boolean> = sliderIsInitializedState

    fun saveSliderIsInitializedState(sliderIsInitializedState: Boolean) {
        state[SLIDER_IS_INITIALIZED_STATE_KEY] = sliderIsInitializedState
    }

    fun onInitialization(locationID: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            liveData.postValue(AppState.Success(usecase.getWeatherGraphData(locationID)))
        }
    }

    fun getWeatherGraphDataByRange(locationID: String, timeFrom: Long, timeTo: Long) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            liveData.postValue(AppState.Success(
                usecase.getWeatherGraphDataByRange(locationID, timeFrom, timeTo)))
        }
    }
}