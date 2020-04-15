package com.lucasdias.search.di

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
import com.lucasdias.shared.di.SHARED_RETROFIT
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

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
    factory {
        getCategoryService(
            get<Retrofit>(named(SHARED_RETROFIT))
        )
    }
}

private fun getCategoryService(retrofit: Retrofit): CategoryService =
    retrofit.create(CategoryService::class.java)
