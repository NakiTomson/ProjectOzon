package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.data.db.InBasketDao
import com.appmarketplace.ozon.data.db.FavoriteProductDao
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.services.APIKEY3
import com.appmarketplace.ozon.data.remote.services.APIKEY4
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.BaseViewModel

import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class DetailsProductViewModel() : BaseViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    init {
        OzonApp.appComponent.inject(detailsProductViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepository: ListProductRepository


    val searchProductsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()
    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    var descriptions:MutableLiveData<String> = MutableLiveData()
    var specifications:MutableLiveData<String> = MutableLiveData()

    fun getListEquivalentProducts(name: String?) {
        if (name == null) return
        if (searchProductsResultList.value?.data == null) {
            launch(Dispatchers.IO) {
                val data = async {
                    listProductRepository.loadSearchProducts(
                        Params.ProductsParams(
                            pathId = name.replace(" ","&search="),
                            pageSize = "20",
                            apikey = APIKEY3,
                            page = "1",
                            typeProduct = OnProductItem.Type.ProductWithName
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    searchProductsResultList.value = data?.await()?.result
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


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}