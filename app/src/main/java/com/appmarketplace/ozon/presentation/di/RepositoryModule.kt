package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.data.db.InBasketDao
import com.appmarketplace.ozon.data.db.FavoriteProductDao
import com.appmarketplace.ozon.data.db.HintProductDao
import com.appmarketplace.ozon.data.db.UserDao
import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.mappers.Mapper
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Named("bestbuy")
    fun provideHomeRepositoryImpl1(@Named("bestbuy") serviceApi: ServerApi.MarketPlaceService,mapper: Mapper): HomeRepository {
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
    @Named("bestbuy")
    fun provide(@Named("bestbuy") serviceApi: ServerApi.MarketPlaceService,mapper: Mapper): ListProductRepository {
        return ListProductRepository(serviceApi,mapper)
    }

    @Provides
    @Named("dropbox")
    fun provideHomeRepositoryImpl2(@Named("dropbox") serviceApi: ServerApi.MarketPlaceService,mapper: Mapper): HomeRepository {
        return HomeRepository(serviceApi,mapper)
    }

}