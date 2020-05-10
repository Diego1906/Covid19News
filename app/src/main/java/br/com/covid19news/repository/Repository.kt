package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.covid19news.data.AppDatabase
import br.com.covid19news.data.asDomainModel
import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.mapper.mapToModel
import br.com.covid19news.remote.IService
import br.com.covid19news.remote.dto.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val service: IService) : IRepository {

    override suspend fun getStatusWorldOrByCountry(filter: String): DataStatisticsModel {
        return service.getService()
            .getStatusWorldOrByCountry(filter)
            .mapToModel()
    }

    override suspend fun getStatusAllCountries(): DataStatisticsModel {
        return service.getService()
            .getStatusAllCountries()
            .mapToModel()
    }
}


class Repository02(private val service: IService, private val database: AppDatabase) {

    val responses: LiveData<List<ResponseModel>> =
        Transformations.map(database.dao.getAll()) {
            it.asDomainModel()
        }

    suspend fun refreshStatistics() {
        withContext(Dispatchers.IO) {
            val dataList = service.getService().getStatusAllCountries()

            val ret = dataList.asDatabaseModel()

            database.dao.insertAll(*ret)
        }
    }
}