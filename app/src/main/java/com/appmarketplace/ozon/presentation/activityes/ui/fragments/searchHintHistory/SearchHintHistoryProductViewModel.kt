package com.appmarketplace.ozon.presentation.activityes.ui.fragments.searchHintHistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class SearchHintHistoryProductViewModel : ViewModel(),CoroutineScope {


    val liveDataHints:MutableLiveData<List<HintProductDB>> = MutableLiveData()


    init {
        OzonApp.appComponent.inject(searchHintProductHomeViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepository

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

    fun getHintsDB(){
        launch(Dispatchers.IO) {
            val hintslist = productDb.hintProductsDao()?.getAll()
            if (hintslist == null || hintslist.isEmpty()){
                getHintText()
            }else{
                liveDataHints.postValue(hintslist.reversed())
            }
        }
    }

    fun setHintDB(hintProductDB: HintProductDB){
        launch(Dispatchers.IO) {
            productDb.hintProductsDao()?.insert(hintProductDB)
        }
    }


    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}

