package com.app.marketPlace.presentation

import android.app.Application
import android.util.Log
import com.app.marketPlace.presentation.di.*
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach


@HiltAndroidApp
class MarketPlaceApp:Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@MarketPlaceApp)
    }
}
