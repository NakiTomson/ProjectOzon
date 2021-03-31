package com.app.marketPlace.presentation.di

import android.content.Context
import androidx.room.Room
import com.app.marketPlace.data.db.MarketPlaceDataBase
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteProductDao
import com.app.marketPlace.data.db.dao.UserDao
import com.app.marketPlace.presentation.MarketPlaceApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    fun provideDataBase( @ApplicationContext context: Context): MarketPlaceDataBase {
        return Room.databaseBuilder(context, MarketPlaceDataBase::class.java, "ozon.db").build()
    }

    @Provides
    fun provideUserDao(db: MarketPlaceDataBase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideHintDao(db: MarketPlaceDataBase): HintProductDao {
        return db.hintProductsDao()
    }

    @Provides
    fun provideProductDao(db: MarketPlaceDataBase): InFavoriteProductDao {
        return db.productsDao()
    }

    @Provides
    fun provideBasketProductDao(db: MarketPlaceDataBase): InBasketDao {
        return db.productsBasketDao()
    }
}