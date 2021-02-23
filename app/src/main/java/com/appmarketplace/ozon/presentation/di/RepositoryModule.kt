package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class RepositoryModule {

    @Provides
    @Named("bestbuy")
    fun provideHomeRepositoryImpl1( @Named("bestbuy") serviceApi: ServerApi.MarketPlaceService): HomeRepository {
        return HomeRepository(serviceApi)
    }

    @Provides
    @Named("bestbuy")
    fun provide(@Named("bestbuy") serviceApi: ServerApi.MarketPlaceService): ListProductRepository {
        return ListProductRepository(serviceApi)
    }

    @Provides
    @Named("dropbox")
    fun provideHomeRepositoryImpl2( @Named("dropbox") serviceApi: ServerApi.MarketPlaceService): HomeRepository {
        return HomeRepository(serviceApi)
    }

}