package com.app.marketPlace.presentation.di

import com.app.marketPlace.data.db.InBasketDao
import com.app.marketPlace.data.db.FavoriteProductDao
import com.app.marketPlace.data.db.HintProductDao
import com.app.marketPlace.data.db.UserDao
import com.app.marketPlace.data.remote.services.MarketPlaceService
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.domain.repositories.HomeRepository

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Named("bestbuy")
    fun provideHomeRepositoryImpl1(@Named("bestbuy") serviceApi: MarketPlaceService, mapper: Mapper): HomeRepository {
        return HomeRepository(serviceApi,mapper)
    }


    @Provides
    fun provideDataBaseRepository(
        productDao: FavoriteProductDao,
        basketDao: InBasketDao,
        userDao: UserDao,
        mapper: Mapper,
        hintProductDao: HintProductDao
    ): DataBaseRepository {
        return DataBaseRepository(productDao, basketDao, userDao, mapper = mapper, hintDao = hintProductDao)
    }

    @Provides
    @Singleton
    fun provideMapper(): Mapper {
        return Mapper()
    }



    @Provides
    @Named("dropbox")
    fun provideHomeRepositoryImpl2(@Named("dropbox") serviceApi: MarketPlaceService,mapper: Mapper): HomeRepository {
        return HomeRepository(serviceApi,mapper)
    }

}