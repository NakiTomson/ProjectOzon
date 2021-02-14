package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.converters.GeneralCategoryConverter
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named


@Module
class RepositoryModule {

    @Provides
    @Named("bestbuy")
    fun provideHomeRepositoryImpl1( @Named("bestbuy") serviceApi: ServerApi.MarketPlaceService,converter: GeneralCategoryConverter): HomeRepositoryImpl {
        return HomeRepositoryImpl(serviceApi,converter)
    }

    @Provides
    @Named("dropbox")
    fun provideHomeRepositoryImpl2( @Named("dropbox") serviceApi: ServerApi.MarketPlaceService,converter: GeneralCategoryConverter): HomeRepositoryImpl {
        return HomeRepositoryImpl(serviceApi,converter)
    }

}