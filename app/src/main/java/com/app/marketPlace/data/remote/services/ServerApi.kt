package com.app.marketPlace.data.remote.services

import com.app.marketPlace.data.remote.modelsAPI.ProductsModel
import com.app.marketPlace.domain.modelsUI.GeneralCategory
import com.app.marketPlace.domain.modelsUI.OnHistoryItem
import com.app.marketPlace.domain.modelsUI.OnLiveItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val APIKEY1 ="CJiicx0Rre1CVBLRQfSjyPwW"

const val APIKEY2 ="J9NSJ6qcKddf7wxPdSIKL4eA"

const val APIKEY3 ="zXSRzd1MQNKNebKntBATTcQj"

const val APIKEY4 ="km1UJHSme0nGgFIkfD5QqdhC"

const val APIKEY5 ="ym8kR5h96jJEZ76EXzpsVZhF"


interface ServerApi{


    interface MarketPlaceService  {

        @GET("/v1/categories?format=json&show=name,id")
        fun getCategoryProductsAsync(
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
            @Query("apiKey") apykey: String
        ): Deferred<GeneralCategory?>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json")
        fun getProductsByCategoryAsync(
                @Path("PathId") pathId:String,
                @Query("pageSize") pageSize: String,
                @Query("page") page: String,
                @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>



        @GET("/v1/products((search={keyword}))?format=json")
        fun getProductsBySearchAsync(
            @Path("keyword") keyword:String,
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
            @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>


        @GET("/s/0w71n1rc6v85s0r/historyData.json?dl=1")
        fun getHistoryAsync(): Deferred<OnHistoryItem>


        @GET("/s/xgal8vjau426dka/liveData.json?dl=1")
        fun getLivesAsync(): Deferred<OnLiveItem>
    }
}