package com.app.photoweather.network_layer

import com.app.photoweather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    companion object {
        private const val SIXTY_SECONDS_TIMEOUT: Long = 60
        private const val baseUrl: String = BuildConfig.WEATHER_BASE_URL


        fun build(): Retrofit {
            val httpClient = getHttpClientBuilder()
            addContentInterceptor(httpClient)
            if (BuildConfig.DEBUG) {
                addLoggingInterceptor(httpClient)
            }
            return getRetrofit(httpClient)
        }

        private fun getRetrofit(httpClient: OkHttpClient.Builder): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }


        private fun getHttpClientBuilder(): OkHttpClient.Builder {
            val httpClient = OkHttpClient().newBuilder()
            httpClient.readTimeout(SIXTY_SECONDS_TIMEOUT, TimeUnit.SECONDS)
            httpClient.writeTimeout(SIXTY_SECONDS_TIMEOUT, TimeUnit.SECONDS)
            httpClient.connectTimeout(SIXTY_SECONDS_TIMEOUT, TimeUnit.SECONDS)
            return httpClient
        }

        private fun addContentInterceptor(httpClient: OkHttpClient.Builder) {
            val contentInterceptor = EmptyContentInterceptor()
            httpClient.interceptors().add(contentInterceptor)
        }

        private fun addLoggingInterceptor(httpClient: OkHttpClient.Builder) {
            val bodyInterceptor = HttpLoggingInterceptor()
            bodyInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.interceptors().add(bodyInterceptor)
        }
    }

}