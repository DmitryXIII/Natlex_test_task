package com.avacodo.natlextesttask.data.remote

import com.avacodo.natlextesttask.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_PARAM_NAME = "appid"
private const val LANGUAGE_PARAM_NAME = "lang"
private const val LANGUAGE_PARAM_VALUE = "ru"

class RetrofitInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val updatedUrl = chain.request().url.newBuilder()
            .addQueryParameter(API_KEY_PARAM_NAME, BuildConfig.OPENWEATHERMAP_API_KEY)
            .addQueryParameter(LANGUAGE_PARAM_NAME, LANGUAGE_PARAM_VALUE)
            .build()

        return chain.proceed(
            chain.request().newBuilder()
                .url(updatedUrl)
                .build()
        )
    }
}