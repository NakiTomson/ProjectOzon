package com.app.marketPlace.presentation.activities.ui.fragments.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BasketViewModel:ViewModel(), CoroutineScope {

    init {
        MarketPlaceApp.appComponent.inject(basketViewModel = this)
    }

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @Inject
    lateinit var repository: AppRepository

    val productsResultList: MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    fun getListSimilarCategory(recommendedPath: String){
        if(productsResultList.value?.data != null) return
        launch(Dispatchers.IO) {
            val products= async {
                repository.loadProducts(
                    Params.ProductsParams(
                        pathId = recommendedPath,
                        pageSize = "20",
                        apiKey = APIKEY,
                        page = "1",
                        typeProduct = ProductItem.Type.ProductWithName
                    )
                )
            }
            withContext(Dispatchers.Main){
                val result = products.await().result
                when(gettingErrors(result)){
                    true -> productsResultList.value = result
                    else -> errorHandling("ERROR PRODUCT IN BASKET ",result)
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}