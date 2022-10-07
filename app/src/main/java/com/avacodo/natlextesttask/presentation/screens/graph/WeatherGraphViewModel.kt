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

    fun getWeatherGraphData(locationID: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            liveData.postValue(AppState.Success(usecase.getWeatherGraphData(locationID)))
        }
    }
}