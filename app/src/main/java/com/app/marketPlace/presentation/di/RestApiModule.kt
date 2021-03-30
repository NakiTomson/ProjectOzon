package com.app.marketPlace.presentation.di

import android.content.Context
import com.app.marketPlace.data.remote.services.MarketPlaceService
import com.app.marketPlace.data.utils.ConstantsApp.baseUrlBestBye
import com.app.marketPlace.presentation.utils.NetworkConnection
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class RestApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClientFactory(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder().build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBestBy(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
                .Builder()
                .baseUrl(baseUrlBestBye)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideMarketPlaceServiceApiDropBox(retrofit: Retrofit): MarketPlaceService {
        return retrofit.create(MarketPlaceService::class.java)
    }

}






