package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.InBasketDao
import com.appmarketplace.ozon.data.db.FavoriteProductDao
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.services.APIKEY4
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class BasketViewModel:ViewModel(), CoroutineScope {


    init {
        OzonApp.appComponent.inject(basketViewModel = this)
    }


    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepository: ListProductRepository


    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()


    fun getListSimilarCategory(recomedDedPath: String){
        if(productsResultList.value?.data == null){
            launch {
                val products= async {
                    listProductRepository.loadProducts(
                        Params.ProductsParams(
                            pathId = recomedDedPath,
                            pageSize = "20",
                            apikey = APIKEY4,
                            page = "1",
                            typeProduct = OnProductItem.Type.ProductWithName
                        )
                    )
                }
                withContext(Dispatchers.Main){
                    productsResultList.value = products.await().result
                }
            }
        }
    }

    private val job: Job = Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}