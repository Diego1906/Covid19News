package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.data.AppDatabase
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.mapper.asDatabaseModel
import br.com.covid19news.mapper.mapToDomainModel
import br.com.covid19news.remote.IService
import br.com.covid19news.util.onIsNetworkConnected

class Repository(private val service: IService, private val database: AppDatabase) : IRepository {

    private val zero = App.getContext().resources.getInteger(R.integer.zero)

    private val _isNotNetworkConnected = MutableLiveData(false)
    override val isNotNetworkConnected
        get() = _isNotNetworkConnected

    override val responses: LiveData<List<ResponseDomainModel>> =
        Transformations.map(database.dao.getAll()) {
            it.mapToDomainModel()
        }

    override suspend fun refreshStatisticsAllCountries() {
        if (database.dao.getCountTotalResult() == zero) {
            if (App.getContext().onIsNetworkConnected())
                onRefreshDatabase()
            else
                onIsNotConnected()
        }
    }

    override suspend fun onRefreshDatabase() {
        val statisticsRemote = service.getService().getStatisticsAllCountries()
        database.dao.insertAll(*statisticsRemote.asDatabaseModel())
    }

    override suspend fun getStatisticsWorldOrByCountry(filter: String): ResponseDomainModel? {
        var response: ResponseDomainModel? = null
        when (database.dao.getCountResult(filter)) {
            zero -> {
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
            .responses[zero]
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