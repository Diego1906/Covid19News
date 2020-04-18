package br.com.covid19news.repository

import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.mapper.mapToModel
import br.com.covid19news.remote.IService
import br.com.covid19news.util.TypeSearch

class Repository(private val service: IService) : IRepository {

    override suspend fun getDataAllCountries(typeSearch: TypeSearch): DataStatisticsModel {
        return service.getService()
            .getDataAllCountries(typeSearch.value)
            .mapToModel()
    }
}