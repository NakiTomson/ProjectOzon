package com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.ProductDao
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel : ViewModel(), CoroutineScope {

    init {
        OzonApp.appComponent.inject(favoriteViewModel = this)
    }


    val productsLive: MutableLiveData<List<ProductDb>> = MutableLiveData()

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    @Inject
    lateinit var productDao: ProductDao


    fun getFavoriteProducts() {
        launch(Dispatchers.IO) {
            val products = productDao?.getAll()
            withContext(Dispatchers.Main){
                productsLive.value = products
            }
        }

    }

    fun deleteProduct(productsItem: OnProductItem) {
        launch(Dispatchers.IO) {
            productDao?.delete(ProductDb(id = productsItem.skuId))
        }
    }


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

}
