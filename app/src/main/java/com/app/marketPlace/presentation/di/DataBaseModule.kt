package com.app.marketPlace.presentation.di

import android.content.Context
import androidx.room.Room
import com.app.marketPlace.data.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBase(context: Context): MarketPlaceAppDataBase {
        return Room.databaseBuilder(context, MarketPlaceAppDataBase::class.java, "ozon.db").build()
    }


    @Singleton
    @Provides
    fun provideUserDao(db: MarketPlaceAppDataBase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideHintDao(db: MarketPlaceAppDataBase): HintProductDao {
        return db.hintProductsDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: MarketPlaceAppDataBase): FavoriteProductDao {
        return db.productsDao()
    }


    @Singleton
    @Provides
    fun provideBasketProductDao(db: MarketPlaceAppDataBase): InBasketDao {
        return db.productsBasketDao()
    }

}