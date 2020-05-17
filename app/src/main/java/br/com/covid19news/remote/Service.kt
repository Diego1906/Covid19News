package br.com.covid19news.remote

import br.com.covid19news.BuildConfig
import br.com.covid19news.R
import br.com.covid19news.application.App
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Service : IService {

    private val retrofit: Retrofit

    init {
        val ten = App.getContext().resources.getInteger(R.integer.ten).toLong()

        val client = OkHttpClient.Builder()
            .connectTimeout(ten, TimeUnit.SECONDS)
            .writeTimeout(ten, TimeUnit.SECONDS)
            .readTimeout(ten, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader(BuildConfig.HEADER_HOST_NAME, BuildConfig.HEADER_HOST_VALUE)
                    .addHeader(BuildConfig.HEADER_API_KEY_NAME, BuildConfig.API_KEY_VALUE)
                    .build()

                chain.proceed(request)
            }.build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    override fun getService(): IServiceAPI {
        return retrofit.create(IServiceAPI::class.java)
    }
}