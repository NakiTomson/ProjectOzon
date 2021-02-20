package com.appmarketplace.ozon.presentation.di

import android.content.Context
import androidx.room.Room
import com.appmarketplace.ozon.data.db.OzonAppDataBase
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

}