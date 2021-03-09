package com.app.marketPlace.presentation.activities.ui.fragments.detail

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.remote.services.APIKEY3
import com.app.marketPlace.data.remote.services.APIKEY4
import com.app.marketPlace.domain.modelsUI.CategoryPath
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel

import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.modelsUI.OnOfferProductsItem
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.ListProductRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class DetailsProductViewModel : BaseViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    init {
        MarketPlaceApp.appComponent.inject(detailsProductViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepository: ListProductRepository


    val searchProductsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()
    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()


    fun getListEquivalentProducts(name: String?) {
        if (name == null) return
        if (searchProductsResultList.value?.data == null) {
            launch(Dispatchers.IO) {
                val data = async {
                    listProductRepository.loadSearchProducts(
                        Params.ProductsParams(
                            pathId = name.replace(" ","&search="),
                            pageSize = "20",
                            apiKey = APIKEY3,
                            page = "1",
                            typeProduct = OnProductItem.Type.ProductWithName
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    searchProductsResultList.value = data.await().result
                }
            }
        }
    }

    fun getListSimilarCategory(category: List<CategoryPath>?){
        if(productsResultList.value?.data == null){
            launch {
                val products= async {
                    listProductRepository
                        .loadProducts(
                            Params.ProductsParams(
                                pathId = category?.get(category.size-1)?.id ?: "null",
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


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}