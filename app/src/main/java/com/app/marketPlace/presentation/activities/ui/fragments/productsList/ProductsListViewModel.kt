package com.app.marketPlace.presentation.activities.ui.fragments.productsList

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel(), CoroutineScope {

    private val _productsList: MutableStateFlow<Resource<CombineProducts>> = MutableStateFlow(
        Resource.getDefSateResource()
    )

    val productsList: StateFlow<Resource<CombineProducts>> = _productsList.asStateFlow()


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
                        apiKey = ApiToken,
                        page = "1",
                        typeProduct = Product.Type.ProductWithName,
                        requestName = requestName
                    )
                )
            }
            _productsList.value = checkingForErrors(products.await().result)
        }
    }
}