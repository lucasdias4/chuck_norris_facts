package com.lucasdias.home.di

import android.content.SharedPreferences
import com.lucasdias.core_components.connectivity.Connectivity
import com.lucasdias.home.data.userfirsttime.UserFirstTimeRepositoryImpl
import com.lucasdias.home.data.userfirsttime.local.UserFirstTimeCache
import com.lucasdias.home.domain.repository.UserFirstTimeRepository
import com.lucasdias.home.domain.usecase.GetIfIsTheUsersFirstTime
import com.lucasdias.home.domain.usecase.SetThatIsNotTheUsersFirstTimeInTheCache
import com.lucasdias.home.pesentation.HomeViewModel
import com.lucasdias.shared.di.SHARED_SHARED_PREFERENCES
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_CONNECTIVITY = "HOME_CONNECTIVITY"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val homeModule = module {

    viewModel {
        HomeViewModel(
            get<GetIfIsTheUsersFirstTime>(),
            get<SetThatIsNotTheUsersFirstTimeInTheCache>()
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
        SetThatIsNotTheUsersFirstTimeInTheCache(
            get<UserFirstTimeRepository>()
        )
    }

    factory {
        UserFirstTimeRepositoryImpl(
            get<UserFirstTimeCache>()
        ) as UserFirstTimeRepository
    }

    factory {
        UserFirstTimeCache(
            get<SharedPreferences>(named(SHARED_SHARED_PREFERENCES))
        )
    }
}
