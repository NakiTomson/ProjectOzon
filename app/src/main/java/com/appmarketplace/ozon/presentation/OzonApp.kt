package com.appmarketplace.ozon.presentation

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.appmarketplace.ozon.presentation.utils.NetworkConnection

class OzonApp:Application() ,LifecycleOwner{

    override fun onCreate() {
        super.onCreate()

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected){

            }else{

            }
        })
    }

    override fun getLifecycle(): Lifecycle {
        TODO()
    }
}