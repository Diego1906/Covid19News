package br.com.covid19news.application

import android.app.Application
import br.com.covid19news.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CovidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CovidApplication)
            modules(appModule)
        }
    }
}