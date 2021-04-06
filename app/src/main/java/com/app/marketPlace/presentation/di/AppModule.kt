package com.app.marketPlace.presentation.di

import android.content.Context
import androidx.room.Room
import com.app.marketPlace.data.db.MarketPlaceDataBase
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteDao
import com.app.marketPlace.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDataBase( @ApplicationContext context: Context): MarketPlaceDataBase {
        return Room.databaseBuilder(context, MarketPlaceDataBase::class.java, "bestbuy.db").build()
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
    fun provideProductDao(db: MarketPlaceDataBase): InFavoriteDao {
        return db.inFavoriteDao()
    }

    @Provides
    fun provideBasketProductDao(db: MarketPlaceDataBase): InBasketDao {
        return db.inBasketDao()
    }
}