package com.appmarketplace.ozon.presentation

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.presentation.di.*
import com.google.firebase.FirebaseApp
import dagger.Provides
import javax.inject.Singleton

class OzonApp:Application() {


    companion object{
        lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(app = this@OzonApp)).build()

        FirebaseApp.initializeApp(this@OzonApp)
    }

}