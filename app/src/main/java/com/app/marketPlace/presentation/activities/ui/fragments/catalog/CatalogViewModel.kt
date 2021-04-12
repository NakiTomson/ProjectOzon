package com.app.marketPlace.presentation.activities.ui.fragments.catalog

import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.Constants.ApiToken
import com.app.marketPlace.data.utils.Constants.bestPath
import com.app.marketPlace.domain.useCases.CategoriesLoadUseCase
import com.app.marketPlace.domain.models.Params
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
class CatalogViewModel @Inject constructor(
    private val useCase: CategoriesLoadUseCase
) : BaseViewModel(), CoroutineScope {

    private val _categoryProduct: MutableStateFlow<Resource<List<Categories>>> = MutableStateFlow(
        Resource.getDefSateResource()
    )

    val categoryProduct: StateFlow<Resource<List<Categories>>> = _categoryProduct.asStateFlow()

    fun getCatalogProducts() {
        loadData {
            if (categoryProduct.value.data != null) return@loadData
            val categories =  async {
                useCase.loadCategories(
                    Params.CategoriesProductParams(pageSize = "40", apiKey = ApiToken, page = "1",
                        pathId = bestPath
                    ))
            }
            _categoryProduct.value = checkingForErrors(categories.await().result)
        }
    }
}