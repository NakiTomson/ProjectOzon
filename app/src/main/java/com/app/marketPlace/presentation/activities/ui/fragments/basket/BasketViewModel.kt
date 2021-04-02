package com.app.marketPlace.presentation.activities.ui.fragments.basket

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.utils.Constants.ApiToken
import com.app.marketPlace.domain.mappers.MapperFromDb
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.factory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val repository: AppRepository,
    val mapperFromDb: MapperFromDb
) : BaseViewModel(), CoroutineScope {

    val productsResultList: MutableLiveData<Resource<CombineProducts>> = MutableLiveData()

    fun getListSimilarCategory(recommendedPath: String){
        if(productsResultList.value?.data != null) return
        launch(Dispatchers.IO) {
            val products= async {
                repository.loadProducts(
                    Params.ProductsParams(
                        pathId = recommendedPath,
                        pageSize = "20",
                        apiKey = ApiToken,
                        page = "1",
                        typeProduct = Product.Type.ProductWithName
                    )
                )
            }
            withContext(Dispatchers.Main){
                val result = products.await().result
                when(gettingErrors(result)){
                    true -> productsResultList.value = result
                    else -> errorHandling("ERROR PRODUCT IN BASKET ",result)
                }
            }
        }
    }
}