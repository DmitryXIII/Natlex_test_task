package com.avacodo.natlextesttask

import android.app.Application
import com.avacodo.natlextesttask.di.appModule
import com.avacodo.natlextesttask.di.retrofitModule
import com.avacodo.natlextesttask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule, viewModelModule, retrofitModule)
        }
    }
}