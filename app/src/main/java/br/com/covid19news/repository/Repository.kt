package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.covid19news.data.AppDatabase
import br.com.covid19news.data.asDomainModel
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.domain.StatisticsModel
import br.com.covid19news.mapper.mapToModel
import br.com.covid19news.remote.IService
import br.com.covid19news.remote.dto.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val service: IService, private val database: AppDatabase) : IRepository {

    override val responses: LiveData<List<ResponseModel>> =
        Transformations.map(database.dao.getAll()) {
            it.asDomainModel()
        }

    override suspend fun refreshStatisticsAllCountries() {
        withContext(Dispatchers.IO) {
            val statisticsRemote = service.getService().getStatisticsAllCountries()
            database.dao.insertAll(*statisticsRemote.asDatabaseModel())
        }
    }

    override suspend fun getStatisticsWorldOrByCountry(filter: String): StatisticsModel {
        return service.getService()
            .getStatisticsWorldOrByCountry(filter)
            .mapToModel()
    }
}