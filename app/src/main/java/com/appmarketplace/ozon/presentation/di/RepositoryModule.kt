package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import com.appmarketplace.ozon.domain.repositories.ListProductRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class RepositoryModule {

    @Provides
    @Named("bestbuy")
    fun provideHomeRepositoryImpl1( @Named("bestbuy") serviceApi: ServerApi.MarketPlaceService): HomeRepositoryImpl {
        return HomeRepositoryImpl(serviceApi)
    }

    @Provides
    @Named("bestbuy")
    fun provide(@Named("bestbuy") serviceApi: ServerApi.MarketPlaceService): ListProductRepositoryImpl {
        return ListProductRepositoryImpl(serviceApi)
    }

    @Provides
    @Named("dropbox")
    fun provideHomeRepositoryImpl2( @Named("dropbox") serviceApi: ServerApi.MarketPlaceService): HomeRepositoryImpl {
        return HomeRepositoryImpl(serviceApi)
    }

}