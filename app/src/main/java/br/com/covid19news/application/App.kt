package br.com.covid19news.application

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.work.*
import br.com.covid19news.di.appModule
import br.com.covid19news.workmanager.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit

class App : MultiDexApplication(), Configuration.Provider {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

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

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            //.setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
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