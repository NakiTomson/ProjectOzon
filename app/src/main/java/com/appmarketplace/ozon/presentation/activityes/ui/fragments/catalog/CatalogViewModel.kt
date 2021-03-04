package com.appmarketplace.ozon.presentation.activityes.ui.fragments.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.remote.services.APIKEY5
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class CatalogViewModel: ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val categoryProductliveData: MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    init {
        OzonApp.appComponent.inject(catalogViewModel = this)
    }

    @Inject
    @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepository

    fun getCatalogProducts() {
        if (categoryProductliveData.value?.data != null) return
        launch(Dispatchers.IO) {
            val categoryResult =  async {
                homeRepositoryImplBestBye.loadCategories(
                    Params.CategoriesProductParams(pageSize = "100", apikey = APIKEY5, page = "1"))
            }
            withContext(Dispatchers.Main){
                categoryProductliveData.value = categoryResult.await().result
            }
        }

    }

}