package com.app.marketPlace.presentation.activities.ui.fragments.detail

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel

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


class DetailsProductViewModel : BaseViewModel(), CoroutineScope {

    val searchProductsResultList: MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()
    val productsResultList: MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    fun getListEquivalentProducts(name: String?) {
        if (name == null && searchProductsResultList.value?.data != null) return

        launch(Dispatchers.IO) {
            val products = async {
                repository.loadSearchProducts(
                    Params.ProductsParams(
                        pathId = name!!.replace(" ","&search="),
                        pageSize = "20",
                        apiKey = APIKEY,
                        page = "1",
                        typeProduct = ProductItem.Type.ProductWithName,
                        requestName = name
                    )
                )
            }
            withContext(Dispatchers.Main) {
                val result = products.await().result
                when(gettingErrors(result)){
                    true -> searchProductsResultList.value = result
                    else -> errorHandling("ERROR PRODUCT 1 EQUIVALENT",result)
                }
            }
        }
    }

    fun getListSimilarCategory(category: List<Categories>?){
        if (productsResultList.value?.data != null) return

        launch(Dispatchers.IO) {
            val products= async {
                repository
                    .loadProducts(
                        Params.ProductsParams(
                            pathId = category?.get(category.size-1)?.id ?: "null",
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
                    else -> errorHandling("ERROR PRODUCT 1 SIMILAR",result)
                }
            }
        }
    }
}