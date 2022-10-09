package com.avacodo.natlextesttask.presentation

sealed class AppState<ResultType> {
    abstract fun handleState(
        onStartLoadingAction: () -> Unit,
        onSuccessAction: (result: ResultType) -> Unit,
        onErrorAction: (message: String) -> Unit,
    )

    class Loading<ResultType> : AppState<ResultType>() {
        override fun handleState(
            onStartLoadingAction: () -> Unit,
            onSuccessAction: (result: ResultType) -> Unit,
            onErrorAction: (message: String) -> Unit,
        ) {
            onStartLoadingAction.invoke()
        }
    }

    class Success<ResultType>(private val result: ResultType) : AppState<ResultType>() {
        override fun handleState(
            onStartLoadingAction: () -> Unit,
            onSuccessAction: (result: ResultType) -> Unit,
            onErrorAction: (message: String) -> Unit,
        ) {
            onSuccessAction.invoke(result)
        }
    }

    class Error<ResultType>(private val message: String) : AppState<ResultType>() {
        override fun handleState(
            onStartLoadingAction: () -> Unit,
            onSuccessAction: (result: ResultType) -> Unit,
            onErrorAction: (message: String) -> Unit,
        ) {
            onErrorAction.invoke(message)
        }
    }
}