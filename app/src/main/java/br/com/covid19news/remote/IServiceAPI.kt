package br.com.covid19news.remote

import br.com.covid19news.remote.dto.StatisticsRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceAPI {

    // Get the current status in the world OR current status for a specific country
    @GET("statistics")
    suspend fun getStatisticsWorldOrByCountry(@Query("country") filter: String): StatisticsRemote

    // Get the current status of all countries
    @GET("statistics")
    suspend fun getStatisticsAllCountries(): StatisticsRemote
}


