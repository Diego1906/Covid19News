package br.com.covid19news.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Service : IService {

    private val API_KEY = "81b8f30ce8msh2655e4470b99c3cp1219a3jsnceec1764dddd"

    private val retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", API_KEY)
                    .build()

                chain.proceed(request)
            }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://covid-193.p.rapidapi.com/")
            .client(client.build())
            .build()
    }

    override fun getService(): IServiceAPI {
        return retrofit.create(IServiceAPI::class.java)
    }
}