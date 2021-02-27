package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BasketViewModel:ViewModel(), CoroutineScope {


    init {
        OzonApp.appComponent.inject(basketViewModel = this)
    }

    @Inject
    lateinit var productDb: OzonAppDataBase


    fun getBasket(){
        productDb.productsDao()
    }

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}