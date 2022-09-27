package com.avacodo.natlextesttask.presentation

sealed class AppState<T> {
    abstract fun handleState(
        actionLoading: () -> Unit,
        actionSuccess: (T) -> Unit,
        actionError: (String) -> Unit,
    )

    class Loading<T>() : AppState<T>() {
        override fun handleState(
            actionLoading: () -> Unit,
            actionSuccess: (T) -> Unit,
            actionError: (String) -> Unit,
        ) {
            actionLoading.invoke()
        }
    }

    class Success<T>(private val result: T) : AppState<T>() {
        override fun handleState(
            actionLoading: () -> Unit,
            actionSuccess: (T) -> Unit,
            actionError: (String) -> Unit,
        ) {
            actionSuccess.invoke(result)
        }
    }

    class Error<T>(private val message: String) : AppState<T>() {
        override fun handleState(
            actionLoading: () -> Unit,
            actionSuccess: (T) -> Unit,
            actionError: (String) -> Unit,
        ) {
            actionError.invoke(message)
        }
    }
}