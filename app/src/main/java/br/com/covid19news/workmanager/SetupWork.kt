package br.com.covid19news.workmanager

import android.os.Build
import androidx.work.*
import br.com.covid19news.application.App
import java.util.concurrent.TimeUnit

class SetupWork {

    /*
     * This function is to configure recurring database update work in conjunction with WorkManager
     */
    companion object {

        fun setupRecurringWork() {
            // These are constraints to request periodic work
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                //.setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

            // This object is configured with the execution interval
            val repeatingRequest =
                PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build()

            // Here is configured the WorkManager
            WorkManager.getInstance(App.getContext()).enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
        }
    }
}