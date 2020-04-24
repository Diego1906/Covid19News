package br.com.covid19news.application

import android.app.Application
import android.content.Context
import br.com.covid19news.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

        Timber.plant(DebugTree())
    }

    companion object {

        private lateinit var INSTANCE: Context

        fun setContext(context: Context) {
            synchronized(App::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    INSTANCE = context
                }
            }
        }

        fun getContext() = INSTANCE
    }
}