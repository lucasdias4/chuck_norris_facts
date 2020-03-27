package com.lucasdias.home.di

import com.lucasdias.connectivity.Connectivity
import com.lucasdias.home.pesentation.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_CONNECTIVITY = "HOME_CONNECTIVITY"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val homeModule = module {
    viewModel {
        HomeViewModel()
    }

    factory(named(HOME_CONNECTIVITY)) {
        Connectivity(
            application = androidApplication()
        )
    }
}
