package com.avacodo.natlextesttask.presentation.screens.weasersearching.backgrounddrawer

class BackgroundDrawerFactory {
    fun provideBackgroundDrawer(temperature: Double): BackgroundDrawer {
        return when {
            temperature < 10 -> {
                LowTempBackground()
            }
            temperature in 10.00..25.00 -> {
                MediumTempBackground()
            }
            else -> {
                HighTempBackground()
            }
        }
    }

    fun provideBackgroundDrawer(): BaseBackgroundDrawer {
        return DefaultBackground()
    }
}