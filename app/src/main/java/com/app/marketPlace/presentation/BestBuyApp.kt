package com.app.marketPlace.presentation

import android.app.Application

import com.app.marketPlace.presentation.di.*
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BestBuyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@BestBuyApp)
    }
}
