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


interface ServerApi{



    val APIKEY1: String
        get() = "xWMAMjSScGQTUyXIprjox2DT"
    val APIKEY2: String
        get() = "KbxPb2DroozcbNWGWaaGCknk"


    interface MarketPlaceService  {

        @GET("/v1/categories?format=json&show=name,id&apiKey=xWMAMjSScGQTUyXIprjox2DT")
        fun getCategoryProducts(
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
        ): Deferred<GeneralCategory?>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json&apiKey=xWMAMjSScGQTUyXIprjox2DT")
        fun getThreeProductsByCategoryKey1(
                @Path("PathId") pathId:String,
                @Query("pageSize") pageSize: String,
                @Query("page") page: String
        ): Deferred<ProductsModel>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json&apiKey=KbxPb2DroozcbNWGWaaGCknk ")
        fun getThreeProductsByCategoryKey2(
            @Path("PathId") pathId:String,
            @Query("pageSize") pageSize: String,
            @Query("page") page: String
        ): Deferred<ProductsModel>


//        -----------------------------------------------------

        @GET("/s/0w71n1rc6v85s0r/historyData.json?dl=1")
        fun getHistorys(): Deferred<OnHistoryItem>


        @GET("/s/xgal8vjau426dka/liveData.json?dl=1")
        fun getLives(): Deferred<OnLiveItem>
    }
}
