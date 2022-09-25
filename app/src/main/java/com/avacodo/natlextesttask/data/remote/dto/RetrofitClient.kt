package com.avacodo.natlextesttask.data.remote.dto

import com.avacodo.natlextesttask.BuildConfig
import com.avacodo.natlextesttask.data.remote.RetrofitInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitClient {
    fun createClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(RetrofitInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(RetrofitInterceptor())
                .build()
        }
    }
}