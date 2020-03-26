package com.lucasdias.search.di

import com.lucasdias.search.data.SearchHistoricRepositoryImpl
import com.lucasdias.search.domain.repository.SearchHistoricRepository
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.SetSearchHistoric
import com.lucasdias.search.presentation.SearchAdapter
import com.lucasdias.search.presentation.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val searchModule = module {

    viewModel {
        SearchViewModel(
                get<GetSearchHistoric>(),
                get<SetSearchHistoric>()
        )
    }

    factory { (method: ((String) -> Unit)?) ->
        SearchAdapter(method)
    }

    factory {
        GetSearchHistoric(get<SearchHistoricRepository>())
    }

    factory {
        SetSearchHistoric(get<SearchHistoricRepository>())
    }

    factory {
        SearchHistoricRepositoryImpl() as SearchHistoricRepository
    }
}
