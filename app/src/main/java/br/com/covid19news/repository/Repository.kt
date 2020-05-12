package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.covid19news.data.AppDatabase
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.mapper.asDatabaseModel
import br.com.covid19news.mapper.mapToDomainModel
import br.com.covid19news.remote.IService

private const val ZERO = 0

class Repository(private val service: IService, private val database: AppDatabase) : IRepository {

    override val responses: LiveData<List<ResponseDomainModel>> =
        Transformations.map(database.dao.getAll()) {
            it.mapToDomainModel()
        }

    override suspend fun refreshStatisticsAllCountries() {
        if (responses.value.isNullOrEmpty()) {
            val statisticsRemote = service.getService().getStatisticsAllCountries()
            database.dao.insertAll(*statisticsRemote.asDatabaseModel())
        }
    }

    override suspend fun getStatisticsWorldOrByCountry(filter: String) =
        when (database.dao.getCountResult(filter)) {
            ZERO -> {
                service.getService().getStatisticsWorldOrByCountry(filter)
                    .mapToDomainModel()
                    .listResponse[ZERO]
            }
            else -> database.dao.getByPrimaryKey(filter).mapToDomainModel()
        }
}