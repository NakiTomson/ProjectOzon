package com.appmarketplace.ozon.presentation.activityes.ui.fragments.search_hint_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.models.HintProductDB
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class SearchHintHistoryProductViewModel : ViewModel(),CoroutineScope {

    val liveDataHints:MutableLiveData<List<HintProductDB>> = MutableLiveData()

    val searchProductsResultList:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    init {
        OzonApp.appComponent.inject(searchHintProductHomeViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepositoryImpl

    @Inject
    lateinit var productDb: OzonAppDataBase

    suspend fun getHintText() {
        withContext(Dispatchers.Main){
            liveDataHints.value = listOf(
                HintProductDB("iPhone 12 Pro Max"),
                HintProductDB("Samsung Tv's"),
                HintProductDB("Printer",)
            )
        }
    }

    fun getHints(){
        launch(Dispatchers.IO) {
            val hintslist = productDb.productsDao()?.getAll()
            if (hintslist == null || hintslist.isEmpty()){
                getHintText()
            }else{
                liveDataHints.postValue(hintslist.reversed())
            }
        }
    }

    fun setHint(hintProductDB: HintProductDB){
        launch(Dispatchers.IO) {
            productDb.productsDao()?.insert(hintProductDB)
        }
    }

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job




}

