package com.appmarketplace.ozon.presentation

import android.app.Application
import com.appmarketplace.ozon.presentation.di.*

class OzonApp:Application() {


    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(app = this@OzonApp)).build()
    }



}