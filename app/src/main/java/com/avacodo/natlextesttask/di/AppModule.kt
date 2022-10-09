package com.avacodo.natlextesttask.di

import com.avacodo.natlextesttask.data.MapperToDomain
import com.avacodo.natlextesttask.data.remote.WeatherSearchRepository
import com.avacodo.natlextesttask.data.remote.WeatherRepositoryImpl
import com.avacodo.natlextesttask.data.local.WeatherDatabase
import com.avacodo.natlextesttask.data.GetWeatherUsecaseImpl
import com.avacodo.natlextesttask.data.remote.OpenWeatherMapApi
import com.avacodo.natlextesttask.data.remote.RetrofitClient
import com.avacodo.natlextesttask.data.WeatherGraphUsecaseImpl
import com.avacodo.natlextesttask.data.graph.WeatherGraphRepository
import com.avacodo.natlextesttask.data.graph.WeatherGraphRepositoryImpl
import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase
import com.avacodo.natlextesttask.domain.usecase.WeatherGraphUsecase
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.AppLocationGlobalManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.NatlexAppLocationGlobalManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.data.AppLocationData
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.data.NatlexAppLocationData
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.AppLocationPermissionManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.permission.NatlexAppLocationPermissionManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.settings.AppLocationSettingsManager
import com.avacodo.natlextesttask.presentation.screens.weasersearching.location.settings.NatlexAppLocationSettingsManager
import com.avacodo.natlextesttask.presentation.screens.graph.WeatherGraphViewModel
import com.avacodo.natlextesttask.presentation.screens.graph.chartbuilder.*
import com.avacodo.natlextesttask.presentation.screens.graph.slider.SliderInitializer
import com.avacodo.natlextesttask.presentation.screens.graph.slider.WeatherGraphSliderInitializer
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"

val appModule = module {
    single<GetWeatherUsecase> { GetWeatherUsecaseImpl(repository = get()) }
    single<WeatherGraphUsecase> { WeatherGraphUsecaseImpl(repository = get()) }

    single<WeatherSearchRepository> {
        WeatherRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            mapper = get())
    }

    single<WeatherGraphRepository> {
        WeatherGraphRepositoryImpl(
            localDataSource = get(),
            mapper = get())
    }

    single { MapperToDomain() }
}

val viewModelModule = module {
    viewModel { WeatherSearchingViewModel(usecase = get(), state = get()) }
    viewModel { WeatherGraphViewModel(usecase = get()) }
}

val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient()
                .create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(RetrofitClient().createClient())
            .build().create(OpenWeatherMapApi::class.java)
    }
}

val roomModule = module {
    single { WeatherDatabase.getUserDatabase(androidContext()).weatherDao }
}

val locationModule = module {
    single<AppLocationGlobalManager> {
        NatlexAppLocationGlobalManager(
            locationSettingsManager = get(),
            locationPermissionManager = get(),
            locationDatasource = get()
        )
    }

    single<AppLocationSettingsManager> { NatlexAppLocationSettingsManager() }
    single<AppLocationPermissionManager> { NatlexAppLocationPermissionManager() }
    single<AppLocationData> { NatlexAppLocationData() }
}

val graphModule = module {
    single<ChartValueFormatter> { WeatherChartValueFormatter() }

    factory<ChartInitializer<WeatherGraphDataDomain>> {
        WeatherChartInitializer(chartValueFormatter = get())
    }

    single<SliderInitializer<WeatherGraphDataDomain>> { WeatherGraphSliderInitializer() }

    factory<ChartBuilder<WeatherGraphDataDomain>> { WeatherChartBuilder(chartInitializer = get()) }
}
