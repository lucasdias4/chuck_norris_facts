package com.lucasdias.chucknorrisfacts

import android.app.Application
import com.lucasdias.factcatalog.di.factCatalogModule
import com.lucasdias.home.di.homeModule
import com.lucasdias.log.LogApp
import com.lucasdias.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Logger

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        LogApp.initialize(BuildConfig.DEBUG)
        startKoin {
            androidContext(this@Application)
            logger(koinLogger())
            modules(
                listOf(
                    factCatalogModule,
                    homeModule,
                    searchModule
                )
            )
        }
    }

    private fun koinLogger(): Logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
}
