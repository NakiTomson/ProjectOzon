package com.app.marketPlace.presentation.di

import android.content.Context
import com.app.marketPlace.presentation.MarketPlaceApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: MarketPlaceApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app
}