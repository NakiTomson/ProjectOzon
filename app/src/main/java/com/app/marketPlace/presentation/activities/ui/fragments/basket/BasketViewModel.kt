package com.app.marketPlace.presentation.activities.ui.fragments.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.remote.services.APIKEY4
import com.app.marketPlace.domain.modelsUI.OnOfferProductsItem
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.ListProductRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class BasketViewModel:ViewModel(), CoroutineScope {


    init {
        MarketPlaceApp.appComponent.inject(basketViewModel = this)
    }


    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepository: ListProductRepository


    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()


    fun getListSimilarCategory(recommendedPath: String){
        if(productsResultList.value?.data == null){
            launch {
                val products= async {
                    listProductRepository.loadProducts(
                        Params.ProductsParams(
                            pathId = recommendedPath,
                            pageSize = "20",
                            apiKey = APIKEY4,
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