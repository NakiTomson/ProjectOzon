package com.appmarketplace.ozon.data.remote.services

import com.appmarketplace.ozon.data.remote.models.ProductsModel
import com.appmarketplace.ozon.presentation.pojo.GeneralCategory
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val APIKEY1 ="YHWpCOGVtCSD3EwvzEk8TgGn"
const val APIKEY2 ="4YlWG5yMWRt7yx9TfKc3vnbS"
const val APIKEY3 ="4RTGGVkV872qNqc7ETQ5vgXS"
const val APIKEY4 ="DKBSXAEXex63XOsLfQmFciDu"

interface ServerApi{


    interface MarketPlaceService  {

        @GET("/v1/categories?format=json&show=name,id&apiKey=$APIKEY1")
        fun getCategoryProducts(
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
        ): Deferred<GeneralCategory?>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json")
        fun getThreeProductsByCategoryKey(
                @Path("PathId") pathId:String,
                @Query("pageSize") pageSize: String,
                @Query("page") page: String,
                @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>




        @GET("/v1/products((search={keyword}))?format=json&apiKey=$APIKEY1")
        fun getSearchProductsKey(
            @Path("keyword") keyword:String,
            @Query("pageSize") pageSize: String,
            @Query("page") page: String
        ): Deferred<ProductsModel>


//        https://api.bestbuy.com/v1/products((search=OnePlus&search=8t))?apiKey=xWMAMjSScGQTUyXIprjox2DT&pageSize=100&format=json

//        https://api.bestbuy.com/v1/products((search=oOnePlus&search=8t))?format=json&apiKey=KbxPb2DroozcbNWGWaaGCknk&pageSize=100&page=1

//        -----------------------------------------------------

        @GET("/s/0w71n1rc6v85s0r/historyData.json?dl=1")
        fun getHistorys(): Deferred<OnHistoryItem>


        @GET("/s/xgal8vjau426dka/liveData.json?dl=1")
        fun getLives(): Deferred<OnLiveItem>
    }
}
