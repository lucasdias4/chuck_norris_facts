package com.lucasdias.chucknorrisfacts

import android.app.Application
import com.lucasdias.factcatalog.di.factCatalogModule
import com.lucasdias.log.LogApp
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
                    factCatalogModule
                )
            )
        }
    }

    private fun koinLogger(): Logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
}
