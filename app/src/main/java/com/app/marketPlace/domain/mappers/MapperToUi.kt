package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.CategoriesProduct
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.domain.repositories.Results
import com.app.marketPlace.presentation.rowType.Resource

interface MapperToUi {

    fun map(category: CategoriesProduct?, params: Params):Resource<List<Categories>>

}