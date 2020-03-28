package com.lucasdias.home.di

import com.lucasdias.connectivity.Connectivity
import com.lucasdias.home.data.userfirsttime.UserFirstTimeRepositoryImpl
import com.lucasdias.home.data.userfirsttime.local.UserFirstTimeCache
import com.lucasdias.home.domain.repository.UserFirstTimeRepository
import com.lucasdias.home.domain.usecase.GetIfIsTheUsersFirstTime
import com.lucasdias.home.domain.usecase.SetThatIsNotTheUserFirstTime
import com.lucasdias.home.pesentation.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_CONNECTIVITY = "HOME_CONNECTIVITY"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val homeModule = module {

    viewModel {
        HomeViewModel(
            get<GetIfIsTheUsersFirstTime>(),
            get<SetThatIsNotTheUserFirstTime>()
        )
    }

    factory(named(HOME_CONNECTIVITY)) {
        Connectivity(
            application = androidApplication()
        )
    }

    factory {
        GetIfIsTheUsersFirstTime(
            get<UserFirstTimeRepository>()
        )
    }

    factory {
        SetThatIsNotTheUserFirstTime(
            get<UserFirstTimeRepository>()
        )
    }

    factory {
        UserFirstTimeRepositoryImpl(
            get<UserFirstTimeCache>(),
            androidContext()
        ) as UserFirstTimeRepository
    }

    factory {
        UserFirstTimeCache()
    }
}
