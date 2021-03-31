package com.app.marketPlace.presentation.activities.ui.fragments.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.ConstantsApp
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.data.utils.ConstantsApp.attrCategoryPathId
import com.app.marketPlace.data.utils.ConstantsApp.attrSearch
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailsProductViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel(), CoroutineScope {


    private val _data: MutableSharedFlow<Resource<CombineProductsItem>> =
        MutableSharedFlow()

    val data: SharedFlow<Resource<CombineProductsItem>> =
        _data.shareIn(viewModelScope,started = SharingStarted.Lazily,replay = 2)


    fun getListEquivalentProducts(name: String){
        if (data.replayCache.any {it.status == Resource.Status.COMPLETED}) return
        loadProducts(name.replace(" ","&search="), attrSearch,name)
    }

    fun getListSimilarCategory(category: String){
        if (data.replayCache.any {it.status == Resource.Status.COMPLETED}) return
        loadProducts(category, attrCategoryPathId)
    }


    private fun loadProducts(category: String, attr: String, requestName: String = ""){
        loadData {
            val products= async {
                repository.loadProducts(
                    Params.ProductsParams(
                        attributes = attr,
                        pathId = category,
                        pageSize = "20",
                        apiKey = APIKEY,
                        page = "1",
                        typeProduct = ProductItem.Type.ProductWithName,
                        requestName = requestName
                    )
                )
            }
            _data.emitAll(
                flow {
                    emit(checkingForErrors(products.await().result))
                }
            )
        }
    }
}