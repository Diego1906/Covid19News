package br.com.covid19news.remote

import br.com.covid19news.remote.dto.DataStatistics
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceAPI {

    // Get the current status in the world OR current status for a specific country
    @GET("statistics")
    suspend fun getStatusWorldOrByCountry(@Query("country") filter: String): DataStatistics

    // Get the current status of all countries
    @GET("statistics")
    suspend fun getStatusAllCountries(): DataStatistics
}


