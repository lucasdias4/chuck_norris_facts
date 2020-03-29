package com.lucasdias.search.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucasdias.search.BuildConfig.FACT_API_URL
import com.lucasdias.search.data.category.CategoryRepositoryImpl
import com.lucasdias.search.data.category.local.CategoryCache
import com.lucasdias.search.data.category.remote.CategoryService
import com.lucasdias.search.data.historic.SearchHistoricRepositoryImpl
import com.lucasdias.search.data.historic.local.SearchHistoricCache
import com.lucasdias.search.domain.repository.CategoryRepository
import com.lucasdias.search.domain.repository.SearchHistoricRepository
import com.lucasdias.search.domain.usecase.GetRandomCategoriesFromDatabase
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.IsCategoryCacheEmpty
import com.lucasdias.search.domain.usecase.SearchCategoriesFromApi
import com.lucasdias.search.domain.usecase.SetSearchHistoric
import com.lucasdias.search.presentation.SearchAdapter
import com.lucasdias.search.presentation.SearchViewModel
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val CATEGORIES_RETROFIT = "CATEGORIES_RETROFIT"
private const val CATEGORIES_OKHTTP = "CATEGORIES_OKHTTP"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val searchModule = module {

    viewModel {
        SearchViewModel(
            get<GetSearchHistoric>(),
            get<SetSearchHistoric>(),
            get<SearchCategoriesFromApi>(),
            get<GetRandomCategoriesFromDatabase>(),
            get<IsCategoryCacheEmpty>()
        )
    }

    factory { (method: ((String) -> Unit)?) ->
        SearchAdapter(method)
    }

    factory {
        GetSearchHistoric(
            get<SearchHistoricRepository>()
        )
    }

    factory {
        SetSearchHistoric(
            get<SearchHistoricRepository>()
        )
    }

    factory {
        SearchCategoriesFromApi(
            get<CategoryRepository>()
        )
    }

    factory {
        GetRandomCategoriesFromDatabase(
            get<CategoryRepository>()
        )
    }

    factory {
        IsCategoryCacheEmpty(
            get<CategoryRepository>()
        )
    }

    factory {
        SearchHistoricRepositoryImpl(
            get<SearchHistoricCache>()
        ) as SearchHistoricRepository
    }

    factory {
        CategoryRepositoryImpl(
            get<CategoryService>(),
            get<CategoryCache>()
        ) as CategoryRepository
    }

    // Persistence
    factory {
        SearchHistoricCache(
            androidContext()
        )
    }

    factory {
        CategoryCache(
            androidContext()
        )
    }

    // Service
    factory(named(CATEGORIES_OKHTTP)) {
        createOkHttpClient()
    }

    single(named(CATEGORIES_RETROFIT)) {
        createRetrofit(
            get<OkHttpClient>(named(CATEGORIES_OKHTTP))
        )
    }

    factory {
        getCategoryService(
            get<Retrofit>(named(CATEGORIES_RETROFIT))
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

private fun getCategoryService(retrofit: Retrofit): CategoryService =
    retrofit.create(CategoryService::class.java)
