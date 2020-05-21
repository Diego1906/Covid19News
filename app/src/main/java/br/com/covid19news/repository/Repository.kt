package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.covid19news.application.App
import br.com.covid19news.data.AppDatabase
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.mapper.asDatabaseModel
import br.com.covid19news.mapper.mapToDomainModel
import br.com.covid19news.remote.IService
import br.com.covid19news.util.onIsNetworkConnected

class Repository(private val service: IService, private val database: AppDatabase) : IRepository {

    companion object {
        private const val ZERO = 0
    }

    private val _isNotNetworkConnected = MutableLiveData(false)
    override val isNotNetworkConnected
        get() = _isNotNetworkConnected

    override val responses: LiveData<List<ResponseDomainModel>> =
        Transformations.map(database.dao.getAll()) {
            it.mapToDomainModel()
        }

    override suspend fun refreshStatisticsAllCountries() {
        if (database.dao.getCountTotalResult() == ZERO) {
            if (App.getContext().onIsNetworkConnected())
                onRefreshDatabase()
            else
                onIsNotConnected()
        }
    }

    override suspend fun onRefreshDatabase() {
        database.dao.insertAll(*service.getService().getStatisticsAllCountries().asDatabaseModel())
    }

    override suspend fun getStatisticsWorldOrByCountry(filter: String): ResponseDomainModel? {
        var response: ResponseDomainModel? = null
        when (database.dao.getCountResult(filter)) {
            ZERO -> {
                if (App.getContext().onIsNetworkConnected())
                    response = getReponseService(filter)
                else
                    onIsNotConnected()
            }
            else -> response = getResponseDatabase(filter)
        }
        return response
    }

    private suspend fun getReponseService(filter: String): ResponseDomainModel {
        return service.getService().getStatisticsWorldOrByCountry(filter)
            .mapToDomainModel()
            .responses[ZERO]
    }

    private fun getResponseDatabase(filter: String): ResponseDomainModel {
        return database.dao.getByPrimaryKey(filter).mapToDomainModel()
    }

    private fun onIsNotConnected() {
        _isNotNetworkConnected.postValue(true)
    }

    override fun onIsNotConnectedComplete() {
        _isNotNetworkConnected.value = null
    }
}