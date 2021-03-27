package com.app.marketPlace.presentation.activities.ui.fragments.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.data.utils.ConstantsApp.BEST_PATH
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CatalogViewModel: BaseViewModel(), CoroutineScope {

    val categoryProductLiveData: MutableLiveData<Resource<List<Categories>>> = MutableLiveData()

    fun getCatalogProducts() {
        if (categoryProductLiveData.value?.data != null) return
        launch(Dispatchers.IO) {
            val categories =  async {
                repository.loadCategories(
                    Params.CategoriesProductParams(pageSize = "40", apiKey = APIKEY, page = "1",pathId = BEST_PATH))
            }
            withContext(Dispatchers.Main){
                val result = categories.await().result
                when(gettingErrors(result)){
                    true -> categoryProductLiveData.value = result
                    else -> errorHandling("ERROR CATEGORIES ",result)
                }
            }
        }
    }
}