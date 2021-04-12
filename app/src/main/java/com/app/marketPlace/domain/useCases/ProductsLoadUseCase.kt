package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.models.Results
import com.app.marketPlace.presentation.factory.Resource
import javax.inject.Inject

class ProductsLoadUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadProducts(params: Params): Results.ProductsResult {
        return try {
            val listProducts = marketPlaceApi
                .getProductsAsync(params.attributes,params.pathId, params.pageSize, params.page, params.apiKey)
                .await()
            Results.ProductsResult(params.mapper.map(listProducts, params) as Resource<CombineProducts>)
        } catch (e: Exception) {
            Results.ProductsResult( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}