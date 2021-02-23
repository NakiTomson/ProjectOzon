package com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.data.remote.services.APIKEY1
import com.appmarketplace.ozon.data.remote.services.APIKEY2
import com.appmarketplace.ozon.domain.mappers.MapProductsToListData
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
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
    lateinit var listProductRepositoryImpl: ListProductRepository


    fun loadProductsByWord(keyWordOne: String){
        if (productsResultList.value?.data == null){
            launch(Dispatchers.IO) {
                val data = async{
                    listProductRepositoryImpl.loadSearchProducts(
                        HomeRepository.Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
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
            val products:Deferred<HomeRepository.Results.ResultProduct<OnOfferProductsItem>> = async {
                listProductRepositoryImpl
                    .loadProducts(
                        HomeRepository.Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
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

