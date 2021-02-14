package com.appmarketplace.ozon.presentation.di

import android.content.Context
import com.appmarketplace.ozon.presentation.OzonApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class AppModule(private val app: OzonApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

}