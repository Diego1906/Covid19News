package br.com.covid19news.repository

import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.mapper.mapToModel
import br.com.covid19news.remote.IService

class Repository(private val service: IService) : IRepository {

    override suspend fun getDataAllCountries(): DataStatisticsModel {
        return service.getService()
            .getDataAllCountries()
            .mapToModel()
    }
}