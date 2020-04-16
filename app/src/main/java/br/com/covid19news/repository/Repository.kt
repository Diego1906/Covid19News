package br.com.covid19news.repository

import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.mapper.mapToModel
import br.com.covid19news.remote.IService

class Repository(private val service: IService) : IRepository {

    override suspend fun getDataAllCountries(typeSearch: String): DataStatisticsModel {
        return service.getService()
            .getDataAllCountries(typeSearch)
            .mapToModel()
    }
}