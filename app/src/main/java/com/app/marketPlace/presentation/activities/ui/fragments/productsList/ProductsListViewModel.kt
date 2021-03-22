package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProductsListViewModel :ViewModel(), CoroutineScope {


    init {
        MarketPlaceApp.appComponent.inject(this)
    }
    @Inject
    lateinit var repository: AppRepository

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val searchProductsResultList: MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val productsResultList: MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()


    fun loadProductsByWord(keyWordOne: String) {
        if (searchProductsResultList.value?.data == null) {
            launch(Dispatchers.IO) {
                val products = async {
                    repository.loadSearchProducts(
                        Params.ProductsParams(
                            pathId = keyWordOne,
                            pageSize = "100",
                            apiKey = APIKEY,
                            page = "1",
                            typeProduct = ProductItem.Type.ProductWithName,
                            requestName = keyWordOne
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    val result = products.await().result
                    when(gettingErrors(result)){
                        true -> searchProductsResultList.value = result
                        else -> errorHandling("ERROR PRODUCT 1",result)
                    }
                }
            }
        }
    }

    fun loadProductsByCategory(category:String){
        if(productsResultList.value?.data == null){
            launch {
                val products= async {
                    repository.loadProducts(
                            Params.ProductsParams(
                                pathId = category,
                                pageSize = "100",
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
                        else -> errorHandling("ERROR PRODUCT 1",result)
                    }
                }
            }
        }
    }
}

