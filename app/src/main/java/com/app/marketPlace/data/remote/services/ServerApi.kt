package com.app.marketPlace.data.remote.services

import com.app.marketPlace.data.remote.modelsAPI.ProductsModel
import com.app.marketPlace.domain.modelsUI.GeneralCategory
import com.app.marketPlace.domain.modelsUI.OnHistoryItem
import com.app.marketPlace.domain.modelsUI.OnLiveItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val APIKEY1 ="o6yWoCED4JFpMvv45axyfGyi"

const val APIKEY2 ="vGl3EXqKru2nx5WnQBt2hAzA"

const val APIKEY3 ="aauvEghFugCCd4G0AaVaBP80"

const val APIKEY4 ="yAawHzk7vxwssz1nY1g1Ghl2"

const val APIKEY5 ="LpHWBU9t15h8aAWezq5dwABr"


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
