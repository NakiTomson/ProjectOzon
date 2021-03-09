package com.app.marketPlace.presentation

import android.app.Application
import android.util.Log
import com.app.marketPlace.presentation.di.*
import com.google.firebase.FirebaseApp

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

