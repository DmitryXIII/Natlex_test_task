package com.avacodo.natlextesttask.presentation.screens.weasersearching

import androidx.lifecycle.MutableLiveData
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

    private val livaLocalData = MutableLiveData<List<WeatherModelDomain>>()

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
        if (locationName.isEmpty()) {
            liveData.postValue(AppState.Error("Введите название города")) //todo: сделать маппер ошибок через ресурсы
        } else {
            liveData.postValue(AppState.Loading())
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                usecase.getRemoteWeather(locationName).also { weatherData ->
                    usecase.addLocalWeatherData(weatherData)
                    liveData.postValue(AppState.Success(weatherData))
                }
            }
        }
    }

    fun searchWeather(latitude: Double, longitude: Double) {
        liveData.postValue(AppState.Loading())
        viewModelScope.launch(coroutineExceptionHandler) {
            liveData.postValue(AppState.Success(usecase.getRemoteWeather(latitude, longitude)))
        }
    }
}