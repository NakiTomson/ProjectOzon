package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import javax.inject.Inject

class ProductsLoadUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadProducts(params: Params): TypeResource.Product {
        return try {
            val listProducts = marketPlaceApi
                .getProductsAsync(params.attributes,params.pathId, params.pageSize, params.page, params.apiKey)
                .await()
            TypeResource.Product(params.mapper.map(listProducts, params) as Resource<CombineProducts>)
        } catch (e: Exception) {
            TypeResource.Product( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun loadHorizontalProducts(params: Params): TypeResource.HorizontalProduct {
        return try {
            val listProducts = marketPlaceApi
                .getProductsAsync(params.attributes,params.pathId, params.pageSize, params.page, params.apiKey)
                .await()
            TypeResource.HorizontalProduct(params.mapper.map(listProducts, params) as Resource<CombineProducts>)
        } catch (e: Exception) {
            TypeResource.HorizontalProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}