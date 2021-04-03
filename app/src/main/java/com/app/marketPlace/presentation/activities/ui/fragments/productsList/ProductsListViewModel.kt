package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import androidx.lifecycle.*
import androidx.paging.*
import com.app.marketPlace.data.utils.Constants
import com.app.marketPlace.data.utils.Constants.ApiToken
import com.app.marketPlace.data.utils.Constants.attrCategoryPathId
import com.app.marketPlace.data.utils.Constants.attrSearch
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.factory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel(), CoroutineScope {


    private var defaultQuery = Params.ProductsParams(typeProduct = Product.Type.ProductWithName,)

    fun setDefault(params:Params.ProductsParams){
        defaultQuery = params
    }

    private val currentQuery by lazy {
        MutableLiveData(defaultQuery)
    }

    val productsList by lazy {
        currentQuery.switchMap { queryString ->
            repository.loadProductsPager(queryString).cachedIn(viewModelScope)
        }
    }
}
