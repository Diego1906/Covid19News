package br.com.covid19news.application

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import br.com.covid19news.di.appModule
import br.com.covid19news.workmanager.SetupWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : MultiDexApplication(), Configuration.Provider {

    companion object {
        private lateinit var INSTANCE: Context

        private fun setContext(context: Context) {
            synchronized(App::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = context
                }
            }
        }

        fun getContext() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

        setContext(applicationContext)

        Timber.plant(DebugTree())

        delayedInit()
    }

    private fun delayedInit() = CoroutineScope(Dispatchers.Default).launch {
        SetupWork.setupRecurringWork()
    }

    /*
     * This setting was created for debugging in WorkManager.
     * For more details, see [https://developer.android.com/topic/libraries/architecture/workmanager/advanced/custom-configuration]
     */
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}