package com.appmarketplace.ozon.data.remote.services

import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.domain.modelsUI.GeneralCategory
import com.appmarketplace.ozon.domain.modelsUI.OnHistoryItem
import com.appmarketplace.ozon.domain.modelsUI.OnLiveItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val APIKEY1 ="quuLJtytSmDAlbQjV4dzrEGA"
const val APIKEY2 ="eFLex68quaCHGJeTwhbLG9tL"
const val APIKEY3 ="UUwMdpTL9aTJ4U9x7963QMp1"

const val APIKEY4 ="3SqqgG05OgQWduTPBjTbyyFP"

const val APIKEY5 ="a9J2OHbAgx89xEc5MbG9d0Cu"


interface ServerApi{


    interface MarketPlaceService  {

        @GET("/v1/categories?format=json&show=name,id")
        fun getCategoryProducts(
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
            @Query("apiKey") apykey: String
        ): Deferred<GeneralCategory?>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json")
        fun getProductsByCategory(
                @Path("PathId") pathId:String,
                @Query("pageSize") pageSize: String,
                @Query("page") page: String,
                @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>



        @GET("/v1/products((search={keyword}))?format=json")
        fun getProductsBySearch(
            @Path("keyword") keyword:String,
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
            @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>


        @GET("/s/0w71n1rc6v85s0r/historyData.json?dl=1")
        fun getHistorys(): Deferred<OnHistoryItem>


        @GET("/s/xgal8vjau426dka/liveData.json?dl=1")
        fun getLives(): Deferred<OnLiveItem>
    }
}
