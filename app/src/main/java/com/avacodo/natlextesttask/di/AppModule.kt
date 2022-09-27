package com.avacodo.natlextesttask.di

import com.avacodo.natlextesttask.data.WeatherRepository
import com.avacodo.natlextesttask.data.WeatherRepositoryImpl
import com.avacodo.natlextesttask.data.remote.GetWeatherUsecaseImpl
import com.avacodo.natlextesttask.data.remote.MapperToDomain
import com.avacodo.natlextesttask.data.remote.OpenWeatherMapApi
import com.avacodo.natlextesttask.data.remote.RetrofitClient
import com.avacodo.natlextesttask.domain.usecase.GetWeatherUsecase
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"

val appModule = module {
    single<GetWeatherUsecase> { GetWeatherUsecaseImpl(repository = get()) }

    single<WeatherRepository> {
        WeatherRepositoryImpl(remoteDataSource = get(),
            mapper = get())
    }

    factory { MapperToDomain() }
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
