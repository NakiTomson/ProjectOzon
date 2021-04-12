package com.app.marketPlace.presentation.activities.ui.fragments.detail

import com.app.marketPlace.data.utils.Constants.ApiToken
import com.app.marketPlace.data.utils.Constants.attrCategoryPathId
import com.app.marketPlace.data.utils.Constants.attrSearch
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.useCases.ProductsLoadUseCase
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.factory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailsProductViewModel @Inject constructor(
    private val useCase: ProductsLoadUseCase
) : BaseViewModel(), CoroutineScope {


    private val _data: MutableSharedFlow<Resource<CombineProducts>> =
        MutableSharedFlow()

    val data: SharedFlow<Resource<CombineProducts>> =
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
                useCase.loadProducts(
                    Params.ProductsParams(
                        attributes = attr,
                        pathId = category,
                        pageSize = "20",
                        apiKey = ApiToken,
                        page = "1",
                        typeProduct = Product.Type.ProductWithName,
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