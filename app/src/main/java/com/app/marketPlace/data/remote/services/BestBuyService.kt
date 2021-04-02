package com.app.marketPlace.data.remote.services

import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.data.remote.models.ProductsList
import com.app.marketPlace.data.remote.models.CategoriesProduct
import com.app.marketPlace.domain.models.LiveStreamItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BestBuyService {

    @GET("/v1/categories{path}?format=json")
    fun getCategoriesProductAsync(
        @Path("path") path: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String,
        @Query("apiKey") apiKey: String,
    ): Deferred<CategoriesProduct?>


    @GET("/v1/products(({attributes}={pathIdOrWord}))?format=json")
    fun getProductsAsync(
        @Path("attributes") attributes: String,
        @Path("pathIdOrWord") pathIdOrWord: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String,
        @Query("apiKey") apiKey: String
    ): Deferred<ProductsList>


    @GET("https://www.dropbox.com/s/0w71n1rc6v85s0r/historyData.json?dl=1")
    fun getStoriesAsync(): Deferred<Stories>


    @GET("https://www.dropbox.com/s/xgal8vjau426dka/liveData.json?dl=1")
    fun getLiveStreamsAsync(): Deferred<LiveStreamItem>
}