package br.com.covid19news.repository

import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.util.TypeSearch

interface IRepository {

    suspend fun getStatusWorldOrByCountry(typeSearch: TypeSearch): DataStatisticsModel
}