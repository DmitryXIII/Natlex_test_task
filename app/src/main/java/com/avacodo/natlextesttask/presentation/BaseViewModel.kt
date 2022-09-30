package com.avacodo.natlextesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel<ResultType>: ViewModel() {
    protected val liveData = MutableLiveData<AppState<ResultType>>()

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        liveData.postValue(AppState.Error(throwable.message.toString()))
    }

    fun getData(): LiveData<AppState<ResultType>> = liveData
}