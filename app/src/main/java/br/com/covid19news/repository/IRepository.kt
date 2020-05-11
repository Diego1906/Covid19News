package br.com.covid19news.repository

import androidx.lifecycle.LiveData
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.domain.StatisticsModel

interface IRepository {

    val responses: LiveData<List<ResponseModel>>

    suspend fun getStatisticsWorldOrByCountry(filter: String): StatisticsModel

    suspend fun refreshStatisticsAllCountries()
}