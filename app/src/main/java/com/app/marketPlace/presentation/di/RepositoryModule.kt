package com.app.marketPlace.presentation.di

import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteProductDao
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.UserDao
import com.app.marketPlace.data.remote.services.MarketPlaceService
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.domain.repositories.AppRepository

import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideHomeRepositoryImpl1(serviceApi: MarketPlaceService): AppRepository {
        return AppRepository(serviceApi)
    }


    @Provides
    fun provideDataBaseRepository(
        productDao: InFavoriteProductDao,
        basketDao: InBasketDao,
        userDao: UserDao,
        hintProductDao: HintProductDao
    ): DataBaseRepository {
        return DataBaseRepository(productDao, basketDao, userDao,hintDao = hintProductDao)
    }

}