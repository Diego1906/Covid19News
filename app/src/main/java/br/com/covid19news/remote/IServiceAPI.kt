package br.com.covid19news.remote

import br.com.covid19news.remote.dto.DataStatistics
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceAPI {

    @GET("statistics")
    suspend fun getDataAllCountries(@Query("country") country: String): DataStatistics
}