package com.avacodo.natlextesttask.presentation.screens.graph

import androidx.lifecycle.viewModelScope
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase
import com.avacodo.natlextesttask.presentation.AppState
import com.avacodo.natlextesttask.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherGraphViewModel(private val usecase: WeatherGraphUsecase) :
    BaseViewModel<WeatherGraphDataDomain>() {

    fun onInitialization(locationID: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            liveData.postValue(AppState.Initial(usecase.getWeatherGraphData(locationID)))
        }
    }

    fun getWeatherGraphDataByRange(locationID: String, timeFrom: Long, timeTo: Long) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            liveData.postValue(AppState.Success(
                usecase.getWeatherGraphDataByRange(locationID, timeFrom, timeTo)))
        }
    }
}