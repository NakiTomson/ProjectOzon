package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.CategoriesProduct
import com.app.marketPlace.data.remote.models.ProductsList
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.rowType.Resource

interface IMapper {

    fun<T> map(data: T?, params: Params):Resource<*>

}