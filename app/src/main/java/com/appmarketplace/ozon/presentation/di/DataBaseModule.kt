package com.appmarketplace.ozon.presentation.di

import android.content.Context
import androidx.room.Room
import com.appmarketplace.ozon.data.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBase(context: Context): OzonAppDataBase {
        return Room.databaseBuilder(context, OzonAppDataBase::class.java, "ozon.db").build()
    }


    @Singleton
    @Provides
    fun provideUserDao(db: OzonAppDataBase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideHintDao(db: OzonAppDataBase): HintProductDao {
        return db.hintProductsDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: OzonAppDataBase): FavoriteProductDao {
        return db.productsDao()
    }


    @Singleton
    @Provides
    fun provideBuasketProductDao(db: OzonAppDataBase): InBasketDao {
        return db.productsBasketDao()
    }

}