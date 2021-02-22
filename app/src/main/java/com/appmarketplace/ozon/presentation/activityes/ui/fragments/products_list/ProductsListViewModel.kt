package com.appmarketplace.ozon.presentation.activityes.ui.fragments.products_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.remote.models.ProductsModel
import com.appmarketplace.ozon.data.remote.services.APIKEY1
import com.appmarketplace.ozon.data.remote.services.APIKEY2
import com.appmarketplace.ozon.data.remote.services.APIKEY3
import com.appmarketplace.ozon.data.utils.Gonfigs
import com.appmarketplace.ozon.domain.mappers.MapProductsToListData
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import com.appmarketplace.ozon.domain.repositories.ListProductRepositoryImpl
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ProductsListViewModel:ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    val searchProductsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()
    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()


    init {
        OzonApp.appComponent.inject(searchHintProductHomeViewModel = this)
    }


    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepositoryImpl: ListProductRepositoryImpl


    fun loadProductsByWord(keyWordOne: String){
        if (productsResultList.value?.data == null){
            launch(Dispatchers.IO) {
                val data = async{
                    listProductRepositoryImpl.loadSearchProducts(
                        HomeRepositoryImpl.Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
                            mapper = MapProductsToListData(type = 2),
                            pathId = keyWordOne,
                            pageSize = "100",
                            apikey = APIKEY1,
                            page = "1"
                        )
                    )
                }
                withContext(Dispatchers.Main){
                    searchProductsResultList.value = data?.await()?.result
                }
            }
        }
    }

    fun getProductsByCategory(category:String){

        launch {
            val products:Deferred<HomeRepositoryImpl.Results.ResultProduct<OnOfferProductsItem>> = async {
                listProductRepositoryImpl
                    .loadProducts(
                        HomeRepositoryImpl.Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
                            mapper = MapProductsToListData(type = 2),
                            pathId = category,
                            pageSize = "100",
                            apikey = APIKEY2,
                            page = "1"
                        )
                    )
            }
            withContext(Dispatchers.Main){
                productsResultList.value = products.await().result
            }
        }
    }


}

