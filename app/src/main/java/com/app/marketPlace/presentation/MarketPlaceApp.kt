package com.app.marketPlace.presentation

import android.app.Application
import android.util.Log
import com.app.marketPlace.presentation.di.*
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class MarketPlaceApp:Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(app = this@MarketPlaceApp)).build()
        FirebaseApp.initializeApp(this@MarketPlaceApp)
    }

}
