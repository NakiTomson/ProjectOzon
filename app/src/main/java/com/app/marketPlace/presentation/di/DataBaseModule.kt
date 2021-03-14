package com.app.marketPlace.presentation.di

import android.content.Context
import androidx.room.Room
import com.app.marketPlace.data.db.*
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteProductDao
import com.app.marketPlace.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): MarketPlaceDataBase {
        return Room.databaseBuilder(context, MarketPlaceDataBase::class.java, "ozon.db").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: MarketPlaceDataBase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideHintDao(db: MarketPlaceDataBase): HintProductDao {
        return db.hintProductsDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: MarketPlaceDataBase): InFavoriteProductDao {
        return db.productsDao()
    }

    @Singleton
    @Provides
    fun provideBasketProductDao(db: MarketPlaceDataBase): InBasketDao {
        return db.productsBasketDao()
    }

}