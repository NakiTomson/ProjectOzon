package com.appmarketplace.ozon.domain.repositories

import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.mappers.Mapper
import com.appmarketplace.ozon.presentation.rowType.Resource

class ListProductRepository(val marketPlaceApi: ServerApi.MarketPlaceService,val mapper: Mapper) {



    suspend fun loadSearchProducts(params: Params): Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsBySearch(params.pathId, params.pageSize, params.page, params.apikey)
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
                .getProductsByCategory(params.pathId, params.pageSize, params.page, params.apikey)
                .await()

            Results.ResultProduct(
                mapper.mapProductFromApiToUiProduct(listProducts,type = params.typeProduct)
            )

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


}