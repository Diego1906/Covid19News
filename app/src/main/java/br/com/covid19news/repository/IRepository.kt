package br.com.covid19news.repository

import br.com.covid19news.domain.DataStatisticsModel

interface IRepository {

    suspend fun getStatusWorldOrByCountry(typeSearch: String): DataStatisticsModel
}