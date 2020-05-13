package br.com.covid19news.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.covid19news.repository.IRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    companion object {
        const val WORK_NAME = "RefreshDataWorker "
    }

    private val repository by inject<IRepository>()

    override suspend fun doWork(): Result {
        return try {
            repository.onRefreshDatabase()
            Result.success()
        } catch (ex: HttpException) {
            Result.failure()
        }
    }
}