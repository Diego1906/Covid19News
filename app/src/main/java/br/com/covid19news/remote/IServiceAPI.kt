package br.com.covid19news.remote

import br.com.covid19news.remote.dto.StatisticsRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceAPI {

    @GET("statistics")
    suspend fun getStatisticsWorldOrByCountry(@Query("country") filter: String): StatisticsRemote

    @GET("statistics")
    suspend fun getStatisticsAllCountries(): StatisticsRemote
}


