package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import br.com.covid19news.domain.ResponseDomainModel

interface IRepository {

    val responses: LiveData<List<ResponseDomainModel>>

    val isNotNetworkConnected: LiveData<Boolean>

    suspend fun getStatisticsWorldOrByCountry(filter: String): ResponseDomainModel?

    suspend fun refreshStatisticsAllCountries()

    suspend fun onRefreshDatabase()

    fun onIsNotConnectedComplete()
}