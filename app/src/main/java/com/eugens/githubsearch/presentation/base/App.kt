package com.eugens.githubsearch.presentation.base

import android.app.Application
import com.eugens.githubsearch.presentation.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    private fun configureDi() =
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(provideComponent())
        }

     private fun provideComponent() = appComponent



}