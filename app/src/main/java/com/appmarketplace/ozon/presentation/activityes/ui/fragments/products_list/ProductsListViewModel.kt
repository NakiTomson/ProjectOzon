package com.appmarketplace.ozon.presentation.activityes.ui.fragments.products_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
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


    init {
        OzonApp.appComponent.inject(searchHintProductHomeViewModel = this)
    }


    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepositoryImpl

    fun startSearchProduct(keyWordOne: String){
        if (searchProductsResultList.value?.data == null){
            launch(Dispatchers.IO) {
                val data = async{
                    homeRepositoryImplBestBye.testLoading(keyWord = keyWordOne)
                }

                withContext(Dispatchers.Main){
                    searchProductsResultList.value = data?.await()?.result
                }
            }
        }

    }

}