package com.app.marketPlace.presentation.activities.ui.fragments.catalog

import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.data.utils.ConstantsApp.bestPath
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class CatalogViewModel: BaseViewModel(), CoroutineScope {

    private val _categoryProduct: MutableStateFlow<Resource<List<Categories>>> = MutableStateFlow(
        Resource.getDefSateResource()
    )

    val categoryProduct: StateFlow<Resource<List<Categories>>> = _categoryProduct.asStateFlow()

    fun getCatalogProducts() {
        loadData {
            if (categoryProduct.value.data != null) return@loadData
            val categories =  async {
                repository.loadCategories(
                    Params.CategoriesProductParams(pageSize = "40", apiKey = APIKEY, page = "1",
                        pathId = bestPath
                    ))
            }
            _categoryProduct.value = checkingForErrors(categories.await().result)
        }
    }
}