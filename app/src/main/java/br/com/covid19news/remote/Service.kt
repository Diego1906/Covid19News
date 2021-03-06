package br.com.covid19news.remote

import br.com.covid19news.BuildConfig
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

    companion object {
        private const val TIMEOUT = 10L
    }

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
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