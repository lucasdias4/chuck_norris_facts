package com.lucasdias.factcatalog.di

import androidx.room.Room
import com.lucasdias.core_components.connectivity.Connectivity
import com.lucasdias.factcatalog.data.FactCatalogRepositoryImpl
import com.lucasdias.factcatalog.data.local.FactCatalogDao
import com.lucasdias.factcatalog.data.local.FactCatalogDatabase
import com.lucasdias.factcatalog.data.remote.FactCatalogService
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.factcatalog.domain.usecase.DeleteAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.GetAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import com.lucasdias.factcatalog.presentation.FactCatalogAdapter
import com.lucasdias.factcatalog.presentation.FactCatalogViewModel
import com.lucasdias.shared.di.SHARED_RETROFIT
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val FACT_CATALOG_VIEW_MODEL = "FACT_CATALOG_VIEW_MODEL"
internal const val FACT_CATALOG_ADAPTER = "FACT_CATALOG_ADAPTER"
internal const val FACT_CATALOG_CONNECTIVITY = "FACT_CATALOG_CONNECTIVITY"
private const val FACT_CATALOG_DAO = "FACT_CATALOG_DAO"
private const val FACT_CATALOG_DATABASE = "FACT_CATALOG_DATABASE"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val factCatalogModule = module {
    viewModel(named(FACT_CATALOG_VIEW_MODEL)) {
        FactCatalogViewModel(
            get<GetAllFactsFromDatabase>(),
            get<SearchFactsBySubjectFromApi>(),
            get<DeleteAllFactsFromDatabase>(),
            get<CoroutineDispatcher>()
        )
    }

    factory(named(FACT_CATALOG_CONNECTIVITY)) {
        Connectivity(
            application = androidApplication()
        )
    }

    factory(named(FACT_CATALOG_ADAPTER)) { (method: ((String) -> Unit)?) ->
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
        FactCatalogRepositoryImpl(
            get<FactCatalogService>(),
            get<FactCatalogDao>(named(FACT_CATALOG_DAO))
        ) as FactCatalogRepository
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

    // Service
    factory {
        getFactCatalogService(
            get<Retrofit>(named(SHARED_RETROFIT))
        )
    }
}

private fun getFactCatalogService(retrofit: Retrofit): FactCatalogService =
    retrofit.create(FactCatalogService::class.java)
