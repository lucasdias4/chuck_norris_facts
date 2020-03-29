package com.lucasdias.factcatalog.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucasdias.connectivity.Connectivity
import com.lucasdias.factcatalog.BuildConfig.FACT_API_URL
import com.lucasdias.factcatalog.data.fact.FactCatalogRepositoryImpl
import com.lucasdias.factcatalog.data.fact.local.FactCatalogDao
import com.lucasdias.factcatalog.data.fact.local.FactCatalogDatabase
import com.lucasdias.factcatalog.data.fact.remote.FactCatalogService
import com.lucasdias.factcatalog.data.searchtext.SearchTextRepositoryImpl
import com.lucasdias.factcatalog.data.searchtext.local.SearchTextCache
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.factcatalog.domain.repository.SearchTextRepository
import com.lucasdias.factcatalog.domain.usecase.DeleteAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.GetActualSearchText
import com.lucasdias.factcatalog.domain.usecase.GetAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import com.lucasdias.factcatalog.domain.usecase.SetActualSearchText
import com.lucasdias.factcatalog.presentation.FactCatalogAdapter
import com.lucasdias.factcatalog.presentation.FactCatalogViewModel
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal const val FACT_CATALOG_CONNECTIVITY = "FACT_CATALOG_CONNECTIVITY"
private const val FACT_CATALOG_RETROFIT = "FACT_CATALOG_RETROFIT"
private const val FACT_CATALOG_OKHTTP = "FACT_CATALOG_OKHTTP"
private const val FACT_CATALOG_DAO = "FACT_CATALOG_DAO"
private const val FACT_CATALOG_DATABASE = "FACT_CATALOG_DATABASE"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val factCatalogModule = module {
    viewModel {
        FactCatalogViewModel(
            get<SearchFactsBySubjectFromApi>(),
            get<GetAllFactsFromDatabase>(),
            get<DeleteAllFactsFromDatabase>(),
            get<GetActualSearchText>(),
            get<SetActualSearchText>()
        )
    }

    factory(named(FACT_CATALOG_CONNECTIVITY)) {
        Connectivity(
            application = androidApplication()
        )
    }

    factory { (method: ((String) -> Unit)?) ->
        FactCatalogAdapter(method)
    }

    factory {
        SearchFactsBySubjectFromApi(
            get<FactCatalogRepository>()
        )
    }

    factory {
        GetAllFactsFromDatabase(
            get<FactCatalogRepository>()
        )
    }

    factory {
        DeleteAllFactsFromDatabase(
            get<FactCatalogRepository>()
        )
    }

    factory {
        GetActualSearchText(
            get<SearchTextRepository>()
        )
    }

    factory {
        SetActualSearchText(
            get<SearchTextRepository>()
        )
    }

    factory {
        FactCatalogRepositoryImpl(
            get<FactCatalogService>(),
            get<FactCatalogDao>(named(FACT_CATALOG_DAO))
        ) as FactCatalogRepository
    }

    factory {
        SearchTextRepositoryImpl(
            SearchTextCache(),
            androidContext()
        ) as SearchTextRepository
    }

    // Persistence
    single(named(FACT_CATALOG_DATABASE)) {
        Room.databaseBuilder(
            androidContext(),
            FactCatalogDatabase::class.java,
            FACT_CATALOG_DATABASE
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single(named(FACT_CATALOG_DAO)) {
        get<FactCatalogDatabase>(named(FACT_CATALOG_DATABASE)).factDao()
    }

    factory {
        SearchTextCache()
    }

    // Service
    factory(named(FACT_CATALOG_OKHTTP)) {
        createOkHttpClient()
    }

    single(named(FACT_CATALOG_RETROFIT)) {
        createRetrofit(
            get<OkHttpClient>(named(FACT_CATALOG_OKHTTP))
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
