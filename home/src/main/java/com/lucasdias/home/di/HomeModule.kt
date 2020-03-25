package com.lucasdias.home.di

import com.lucasdias.home.pesentation.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val FACT_CATALOG_RETROFIT = "FACT_CATALOG_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val homeModule = module {
    viewModel {
        HomeViewModel()
    }
}
