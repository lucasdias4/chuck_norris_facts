package com.lucasdias.factcatalog.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucasdias.factcatalog.BuildConfig.FACT_API_URL
import com.lucasdias.factcatalog.data.fact.FactCatalogRepositoryImpl
import com.lucasdias.factcatalog.data.fact.remote.FactCatalogService
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import com.lucasdias.factcatalog.presentation.FactCatalogViewModel
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FACT_CATALOG_RETROFIT = "FACT_CATALOG_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val factCatalogModule = module {
    viewModel {
        FactCatalogViewModel(
            get<SearchFactsBySubjectFromApi>()
        )
    }

    factory {
        SearchFactsBySubjectFromApi(
            get<FactCatalogRepository>()
        )
    }

    factory {
        FactCatalogRepositoryImpl(
            get<FactCatalogService>()
        ) as FactCatalogRepository
    }

    // Service
    factory {
        createOkHttpClient()
    }

    single(named(FACT_CATALOG_RETROFIT)) {
        createRetrofit(
            get<OkHttpClient>()
        )
    }

    factory {
        getFactCatalogService(
            get<Retrofit>(named(FACT_CATALOG_RETROFIT))
        )
    }
}

private fun createOkHttpClient(): OkHttpClient {
    val timeoutInSeconds = 10L
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(FACT_API_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

private fun getFactCatalogService(retrofit: Retrofit): FactCatalogService =
    retrofit.create(FactCatalogService::class.java)
