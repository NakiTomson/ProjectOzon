package com.app.marketPlace.presentation.di

import com.app.marketPlace.data.remote.services.ServerApi
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
                val url = originalHttpUrl.newBuilder()
//                        .addQueryParameter("apiKey", "VZVWfwM3TwxNTbFvNiAxuCPL")
                        .build()
                val requestBuilder = original.newBuilder()
                        .url(url)
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
    @Named("bestbuy")
    @Singleton
    fun provideRetrofitBestBy(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
                .Builder()
                .baseUrl("https://api.bestbuy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Named("dropbox")
    @Singleton
    fun provideRetrofitDropBox(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit
                .Builder()
                .baseUrl("https://www.dropbox.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Named("dropbox")
    @Singleton
    fun provideMarketPlaceServiceApiBestBye(@Named("dropbox") retrofit: Retrofit): ServerApi.MarketPlaceService {
        return retrofit.create(ServerApi.MarketPlaceService::class.java)
    }

    @Provides
    @Named("bestbuy")
    @Singleton
    fun provideMarketPlaceServiceApiDropBox(@Named("bestbuy") retrofit: Retrofit): ServerApi.MarketPlaceService {
        return retrofit.create(ServerApi.MarketPlaceService::class.java)
    }

}






