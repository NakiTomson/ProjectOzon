package com.appmarketplace.ozon.domain.repositories

import com.appmarketplace.ozon.data.remote.services.APIKEY1
import com.appmarketplace.ozon.data.remote.services.APIKEY2
import com.appmarketplace.ozon.data.utils.Gonfigs
import com.appmarketplace.ozon.domain.mappers.MapperUI
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.domain.repositories.Params.CategoriesProductParam
import com.appmarketplace.ozon.domain.repositories.Params.ProductsParam

interface AppRepository<Params,Result> {

    suspend fun <T, M> loadCategories(params: CategoriesProductParam<T, M>): Results.ResultCategoryProduct<T>?
    suspend fun <T, M> loadProducts(params: ProductsParam<T,M>): Results.ResultProduct<T>
}


sealed class Params{

    data class CategoriesProductParam<T,M>(
        val mapper: MapperUI<T, M>,
        val pageSize: String = "3",
        val apikey: String = APIKEY1,
        val page: String = "1"
    ) : Params()

    data class ProductsParam<T,M>(
        val mapper: MapperUI<T, M>,
        val pathId: String = Gonfigs.HOME_AUDIO,
        val pageSize: String = "3",
        val apikey: String = APIKEY2,
        val page: String = "1"
    ) : Params()
}


sealed class Results {
    data class ResultCategoryProduct<V>(val result: Resource<V>):Results()
    data class ResultBanner(val result : Resource<MutableList<OnBoardingItem>>):Results()
    data class ResultHistory(val result : Resource<OnHistoryItem>):Results()
    data class ResultLive(val result : Resource<OnLiveItem>):Results()
    data class ResultProduct<V>(val result : Resource<V>):Results()
}