package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import javax.inject.Inject

class CategoriesLoadUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadCategories(params: Params): TypeResource.Category {
        return try {
            val listGeneralCategory = marketPlaceApi
                .getCategoriesProductAsync(path = params.pathId,pageSize = params.pageSize, page = params.page, apiKey = params.apiKey)
                .await()


//            params.mapper.map2<CategoriesProduct,List<Categories>>(listGeneralCategory, params)

            TypeResource.Category(
                params.mapper.map(listGeneralCategory, params) as Resource<List<Categories>>
            )
        } catch (e: Exception) {
            TypeResource.Category(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}