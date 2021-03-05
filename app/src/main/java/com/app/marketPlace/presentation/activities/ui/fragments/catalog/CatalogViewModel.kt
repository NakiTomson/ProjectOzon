package com.app.marketPlace.presentation.activities.ui.fragments.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.remote.services.APIKEY5
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import com.app.marketPlace.domain.repositories.HomeRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class CatalogViewModel: ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val categoryProductLiveData: MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    init {
        MarketPlaceApp.appComponent.inject(catalogViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepository

    fun getCatalogProducts() {
        if (categoryProductLiveData.value?.data != null) return
        launch(Dispatchers.IO) {
            val categoryResult =  async {
                homeRepositoryImplBestBye.loadCategories(
                    Params.CategoriesProductParams(pageSize = "100", apiKey = APIKEY5, page = "1"))
            }
            withContext(Dispatchers.Main){
                categoryProductLiveData.value = categoryResult.await().result
            }
        }

    }

}