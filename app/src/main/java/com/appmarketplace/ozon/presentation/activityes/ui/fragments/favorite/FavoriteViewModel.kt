package com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.db.ProductDao
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel : ViewModel(), CoroutineScope {

    init {
        OzonApp.appComponent.inject(favoriteViewModel = this)
    }


    @Inject
    lateinit var productDao: ProductDao


    val productsLive: MutableLiveData<List<ProductDb>> = MutableLiveData()

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getFavoriteProducts() {
        if (productsLive.value == null){
            launch(Dispatchers.IO) {
                val products = OzonApp.db.productsDao()?.getAll()
                productsLive.postValue(products)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}