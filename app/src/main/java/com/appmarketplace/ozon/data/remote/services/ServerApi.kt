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


const val APIKEY1 ="fhwGURt5JB5QwJki0w2ntVbp"
const val APIKEY2 ="ugv8FfkQcdokWJBI0JloWvu5"
const val APIKEY3 ="GAnw0tKmAB5Gj4CZ8BBt4QXc"

const val APIKEY4 ="gB2xw3bN5fnVAgKCzlBNBkfG"

const val APIKEY5 ="hq3DQlQXqqNac7opIGAXzm3w"

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
