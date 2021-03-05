package com.app.marketPlace.domain.repositories

import com.app.marketPlace.data.remote.services.ServerApi
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.presentation.rowType.Resource

class ListProductRepository(private val marketPlaceApi: ServerApi.MarketPlaceService, val mapper: Mapper) {



    suspend fun loadSearchProducts(params: Params): Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsBySearchAsync(params.pathId, params.pageSize, params.page, params.apiKey)
                .await()

            Results.ResultProduct(
                mapper.mapProductFromApiToUiProduct(listProducts,type = params.typeProduct)
            )

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadProducts(params: Params.ProductsParams): Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsByCategoryAsync(params.pathId, params.pageSize, params.page, params.apiKey)
                .await()

            Results.ResultProduct(
                mapper.mapProductFromApiToUiProduct(listProducts,type = params.typeProduct)
            )

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


}