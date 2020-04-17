package com.lucasdias.shared.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucasdias.shared.BuildConfig.FACT_API_URL
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SHARED_RETROFIT = "SHARED_RETROFIT"
const val SHARED_OKHTTP = "SHARED_OKHTTP"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val sharedModule = module {

    factory {
        getCoroutinesDispatchersIo()
    }

    // Service
    factory(named(SHARED_OKHTTP)) {
        createOkHttpClient()
    }

    single(named(SHARED_RETROFIT)) {
        createRetrofit(
            get<OkHttpClient>(named(SHARED_OKHTTP))
        )
    }
}

fun createOkHttpClient(): OkHttpClient {
    val timeoutInSeconds = 10L
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(FACT_API_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

private fun getCoroutinesDispatchersIo() = Dispatchers.IO
