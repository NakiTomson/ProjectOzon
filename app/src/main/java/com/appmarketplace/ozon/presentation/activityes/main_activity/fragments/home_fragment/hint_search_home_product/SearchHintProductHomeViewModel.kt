package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.hint_search_home_product

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class SearchHintProductHomeViewModel : ViewModel(),CoroutineScope {

    val liveDataHints:MutableLiveData<List<String>> = MutableLiveData()

    val searchProductsResultList:MutableLiveData<Resource<MutableList<OnOfferProductsItem>>> = MutableLiveData()

    init {
        OzonApp.appComponent.inject(searchHintProductHomeViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepositoryImpl


    fun getHintText() {
        liveDataHints.value = listOf(
            "Туш для ресниц",
            "Iassie",
            "Лего френдс",
            "Перчатки одноразовые",
            "Детските игрушки",
            "Футбольный мяч",
            "Телефон",
            "Сумочка",
        )
    }



    fun startSearchProduct(keyWordOne: String){
        launch(Dispatchers.IO) {
            val data = async{
                homeRepositoryImplBestBye.testLoading(keyWord = keyWordOne)
            }

            withContext(Dispatchers.Main){
                searchProductsResultList.value = data?.await()?.result
            }
        }
    }


    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job




}

