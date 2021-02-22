package com.appmarketplace.ozon.domain.repositories

import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.presentation.data.Resource

class ListProductRepositoryImpl(val marketPlaceApi: ServerApi.MarketPlaceService)
    :AppRepository<HomeRepositoryImpl, HomeRepositoryImpl>{


    suspend fun<T,M> loadSearchProducts(params: Params.ProductsParam<T,M>): Results.ResultProduct<T> {
        return try {

            val listProducts = marketPlaceApi
                .getProductsBySearch(params.pathId, params.pageSize, params.page, params.apikey)
                .await()

            Results.ResultProduct(params.mapper.map(listProducts as M))

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    override suspend fun<T,M> loadProducts(params: Params.ProductsParam<T,M>): Results.ResultProduct<T> {
        return try {

            val listProducts = marketPlaceApi
                .getProductsByCategory(params.pathId, params.pageSize, params.page, params.apikey)
                .await()

            Results.ResultProduct(params.mapper.map(listProducts as M))

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    override suspend fun <T, M> loadCategories(params: Params.CategoriesProductParam<T, M>): Results.ResultCategoryProduct<T>? {
        return  null
    }

}