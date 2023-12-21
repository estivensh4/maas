package com.estivensh4.maasapp

import android.app.Application
import com.estivensh4.maasapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MaasApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MaasApp)
            modules(appModules)
        }
    }
}