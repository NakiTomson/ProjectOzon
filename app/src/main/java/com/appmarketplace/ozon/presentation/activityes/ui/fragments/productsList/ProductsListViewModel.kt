package com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.remote.services.APIKEY1
import com.appmarketplace.ozon.data.remote.services.APIKEY2
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.Params
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ProductsListViewModel(private val repository: ListProductRepository) :ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    val searchProductsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()
    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()




    fun loadProductsByWord(keyWordOne: String) {
        if (searchProductsResultList.value?.data == null) {
            launch(Dispatchers.IO) {
                val data = async {
                    repository.loadSearchProducts(
                        Params.ProductsParams(
                            pathId = keyWordOne,
                            pageSize = "100",
                            apikey = APIKEY1,
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

    fun getProductsByCategory(category:String){
        if(productsResultList.value?.data == null){
            launch {
                val products= async {
                    repository
                        .loadProducts(
                            Params.ProductsParams(
                                pathId = category,
                                pageSize = "100",
                                apikey = APIKEY2,
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


}

