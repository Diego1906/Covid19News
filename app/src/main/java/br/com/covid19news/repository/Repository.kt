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

private const val ZERO = 0

class Repository(private val service: IService, private val database: AppDatabase) : IRepository {

    private val _isNotNetworkConnected = MutableLiveData(false)
    override val isNotNetworkConnected
        get() = _isNotNetworkConnected

    override val responses: LiveData<List<ResponseDomainModel>> =
        Transformations.map(database.dao.getAll()) {
            it.mapToDomainModel()
        }

    override suspend fun refreshStatisticsAllCountries() {
        if (totalResult()) {
            when (onIsNetworkConnected()) {
                true -> {
                    val statisticsRemote = service.getService().getStatisticsAllCountries()
                    database.dao.insertAll(*statisticsRemote.asDatabaseModel())
                }
                else -> {
                    onIsNotConnected()
                }
            }
        }
    }

    override suspend fun getStatisticsWorldOrByCountry(filter: String): Pair<Boolean, Any?> {
        var response: Any? = null
        var isOk = true
        when (database.dao.getCountResult(filter)) {
            ZERO -> {
                if (onIsNetworkConnected()) {
                    response = getReponseService(filter)
                } else {
                    isOk = false
                    onIsNotConnected()
                }
            }
            else -> {
                response = getResponseDatabase(filter)
            }
        }
        return Pair(isOk, response)
    }

    private suspend fun getReponseService(filter: String): ResponseDomainModel {
        return service.getService().getStatisticsWorldOrByCountry(filter)
            .mapToDomainModel()
            .listResponse[ZERO]
    }

    private fun getResponseDatabase(filter: String): ResponseDomainModel {
        return database.dao.getByPrimaryKey(filter).mapToDomainModel()
    }

    private fun totalResult() = database.dao.getCountTotalResult() == ZERO

    private fun onIsNetworkConnected() = App.getContext().onIsNetworkConnected()

    private fun onIsNotConnected() {
        _isNotNetworkConnected.postValue(true)
    }

    override fun onIsNotConnectedComplete() {
        _isNotNetworkConnected.value = null
    }
}