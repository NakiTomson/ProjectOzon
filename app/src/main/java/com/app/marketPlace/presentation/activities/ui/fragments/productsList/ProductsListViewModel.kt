package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.cachedIn
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
