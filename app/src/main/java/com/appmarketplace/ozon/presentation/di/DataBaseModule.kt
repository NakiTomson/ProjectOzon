package com.appmarketplace.ozon.presentation.di

import android.content.Context
import androidx.room.Room
import com.appmarketplace.ozon.data.db.HintsProductsDao
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.db.ProductDao
import com.appmarketplace.ozon.data.db.UserDao
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
    fun provideHintDao(db: OzonAppDataBase): HintsProductsDao {
        return db.hintProductsDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: OzonAppDataBase): ProductDao {
        return db.productsDao()
    }

}