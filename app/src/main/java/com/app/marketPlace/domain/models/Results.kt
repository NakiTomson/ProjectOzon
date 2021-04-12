package com.app.marketPlace.domain.models

import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.presentation.factory.Resource

sealed class Results(open val result: Resource<*>) {
    data class CategoriesProductResult(override val result: Resource<List<Categories>>): Results(result)
    data class BannersResult(override val result : Resource<MutableList<Banner>>): Results(result)
    data class StoriesResult(override val result : Resource<Stories>): Results(result)
    data class LiveStreamsResult(override val result : Resource<LiveStreamItem>): Results(result)
    data class ProductsResult(override val result : Resource<CombineProducts>): Results(result)
}