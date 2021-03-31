package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.data.utils.ConstantsApp.attrCategoryPathId
import com.app.marketPlace.data.utils.ConstantsApp.attrSearch
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel(), CoroutineScope {

    private val _productsList: MutableStateFlow<Resource<CombineProductsItem>> = MutableStateFlow(
        Resource.getDefSateResource()
    )

    val productsList: StateFlow<Resource<CombineProductsItem>> = _productsList.asStateFlow()


    fun loadProductsByWord(keyWord: String) {
        loadProducts(keyWord, attrSearch, keyWord)
    }

    fun loadProductsByCategory(category: String) {
        loadProducts(category, attrCategoryPathId)
    }

    private fun loadProducts(category: String, attr: String, requestName: String = "") {
        if (productsList.value.data != null) return
        loadData {
            val products = async {
                repository.loadProducts(
                    Params.ProductsParams(
                        attributes = attr, pathId = category, pageSize = "100",
                        apiKey = APIKEY,
                        page = "1",
                        typeProduct = ProductItem.Type.ProductWithName,
                        requestName = requestName
                    )
                )
            }
            _productsList.value = checkingForErrors(products.await().result)
        }
    }
}